package server

import (
	"database/sql"
	"fmt"
	"math"
	"net/http"
	"strconv"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/google/uuid"
	"github.com/gorilla/feeds"
	"github.com/parfentjev/simple-blog/internal/config"
	"github.com/parfentjev/simple-blog/internal/db"
)

func (h *RequestHandler) GetPostsPublished(c *gin.Context, params GetPostsPublishedParams) {
	requestedPage, err := strconv.ParseInt(c.DefaultQuery("page", "1"), 10, 64)
	if err != nil {
		c.JSON(http.StatusBadRequest, MessageResponse{"page number should be integer"})
		return
	}

	rows, err := h.queries.SelectVisiblePosts(c.Request.Context(), db.SelectVisiblePostsParams{
		Limit:  h.config.ItemLimit,
		Offset: calculateOffset(requestedPage, h.config.ItemLimit),
	})
	if err != nil {
		panic(err)
	}

	posts := make([]PostPreviewDto, len(rows))
	for i, row := range rows {
		posts[i] = PostPreviewDto{
			Id:      row.ID.String(),
			Title:   row.Title.String,
			Summary: row.Summary.String,
			Date:    row.Date.Time.Format(time.RFC3339),
			Visible: row.Visible.Bool,
		}
	}

	c.JSON(http.StatusOK, PagePostDto{
		Page:       requestedPage,
		TotalPages: getTotalPages(c, h.queries, false, h.config.ItemLimit),
		Items:      posts,
	})
}

func (h *RequestHandler) GetPostsPublishedId(c *gin.Context, id string) {
	row, err := h.queries.SelectVisiblePost(c.Request.Context(), uuid.MustParse(id))
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	c.JSON(http.StatusOK, PostDto{
		Id:      row.ID.String(),
		Title:   row.Title.String,
		Summary: row.Summary.String,
		Text:    row.Text.String,
		Date:    row.Date.Time.Format(time.RFC3339),
		Visible: row.Visible.Bool,
	})
}

func (h *RequestHandler) GetRssPosts(c *gin.Context) {
	posts, err := h.queries.SelectVisiblePosts(c.Request.Context(), db.SelectVisiblePostsParams{
		Limit:  20,
		Offset: 0,
	})
	if err != nil {
		panic(err)
	}

	feed, err := generateRssFeed(posts, h.config)
	if err != nil {
		panic(err)
	}

	c.Data(http.StatusOK, "application/rss+xml", []byte(feed))
}

func (h *RequestHandler) GetPostsEditor(c *gin.Context, params GetPostsEditorParams) {
	requestedPage, err := strconv.ParseInt(c.DefaultQuery("page", "1"), 10, 64)
	if err != nil {
		c.JSON(http.StatusBadRequest, MessageResponse{"page number should be integer"})
		return
	}

	rows, err := h.queries.SelectAllPosts(c.Request.Context(), db.SelectAllPostsParams{
		Limit:  h.config.ItemLimit,
		Offset: calculateOffset(requestedPage, h.config.ItemLimit),
	})
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	posts := make([]PostPreviewDto, len(rows))
	for i, row := range rows {
		posts[i] = PostPreviewDto{
			Id:      row.ID.String(),
			Title:   row.Title.String,
			Summary: row.Summary.String,
			Date:    row.Date.Time.Format(time.RFC3339),
			Visible: row.Visible.Bool,
		}
	}

	c.JSON(http.StatusOK, PagePostDto{
		Page:       requestedPage,
		TotalPages: getTotalPages(c, h.queries, true, h.config.ItemLimit),
		Items:      posts,
	})
}

func (h *RequestHandler) PostPostsEditor(c *gin.Context) {
	var request PostPostsEditorJSONRequestBody
	if nil != c.Bind(&request) {
		c.Status(http.StatusBadRequest)
		return
	}

	if err := h.queries.InsertPost(c.Request.Context(), db.InsertPostParams{
		Title:   sql.NullString{String: request.Title, Valid: true},
		Summary: sql.NullString{String: request.Summary, Valid: true},
		Text:    sql.NullString{String: request.Text, Valid: true},
		Date:    sql.NullTime{Time: time.Now(), Valid: true},
		Visible: sql.NullBool{Bool: request.Visible, Valid: true},
	}); err != nil {
		panic(err)
	}

	c.Status(http.StatusCreated)
}

func (h *RequestHandler) GetPostsEditorId(c *gin.Context, id string) {
	row, err := h.queries.SelectPost(c.Request.Context(), uuid.MustParse(id))
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	c.JSON(http.StatusOK, PostDto{
		Id:      row.ID.String(),
		Title:   row.Title.String,
		Summary: row.Summary.String,
		Text:    row.Text.String,
		Date:    row.Date.Time.Format(time.RFC3339),
		Visible: row.Visible.Bool,
	})
}

func (h *RequestHandler) PutPostsEditorId(c *gin.Context, id string) {
	if _, err := h.queries.SelectPost(c.Request.Context(), uuid.MustParse(id)); err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	var request PutPostsEditorIdJSONRequestBody
	if nil != c.Bind(&request) {
		c.Status(http.StatusBadRequest)
		return
	}

	date, err := time.Parse(time.RFC3339, *request.Date)
	if err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	if err := h.queries.UpdatePost(c.Request.Context(), db.UpdatePostParams{
		Title:   sql.NullString{String: request.Title, Valid: true},
		Summary: sql.NullString{String: request.Summary, Valid: true},
		Text:    sql.NullString{String: request.Text, Valid: true},
		Date:    sql.NullTime{Time: date, Valid: true},
		Visible: sql.NullBool{Bool: request.Visible, Valid: true},
		ID:      uuid.MustParse(id),
	}); err != nil {
		panic(err)
	}

	c.Status(http.StatusOK)
}

func (h *RequestHandler) DeletePostsEditorId(c *gin.Context, id string) {
	if _, err := h.queries.SelectPost(c.Request.Context(), uuid.MustParse(id)); err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	if err := h.queries.DeletePost(c.Request.Context(), uuid.MustParse(id)); err != nil {
		panic(err)
	}

	c.Status(http.StatusOK)
}

func generateRssFeed(posts []db.SelectVisiblePostsRow, config *config.Config) (string, error) {
	feed := &feeds.Feed{
		Title:       config.RssFeedTitle,
		Link:        &feeds.Link{Href: config.RssFeedBaseUrl},
		Description: config.RssFeedDescription,
		Created:     time.Now(),
	}

	for _, p := range posts {
		feed.Items = append(feed.Items, &feeds.Item{
			Id:          p.ID.String(),
			Title:       p.Title.String,
			Description: p.Summary.String,
			Link:        &feeds.Link{Href: fmt.Sprintf("%v/post/%v", config.RssFeedBaseUrl, p.ID)},
			Created:     p.Date.Time,
		})
	}

	return feed.ToAtom()
}

func calculateOffset(requestedPage int64, itemLimit int64) int64 {
	return requestedPage*itemLimit - itemLimit
}

func getTotalPages(c *gin.Context, q *db.Queries, includeDrafts bool, itemLimit int64) int64 {
	var (
		totalPosts int64
		err        error
	)

	if includeDrafts {
		totalPosts, err = q.CountAllPost(c)
	} else {
		totalPosts, err = q.CountPublishedPost(c)
	}

	if err != nil {
		return 0
	}

	return int64(math.Ceil(float64(totalPosts) / float64(itemLimit)))
}
