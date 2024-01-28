package api

import (
	"fmt"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/google/uuid"
	"github.com/parfentjev/simple-blog/internal/db"
	"github.com/parfentjev/simple-blog/internal/helper"
)

func (h *StorageHandler) GetPostsPublished(c *gin.Context) {
	rows, err := h.Queries.SelectVisiblePosts(c.Request.Context())
	if err != nil {
		panic(err)
	}

	posts := make([]PostPreviewDto, len(rows))
	for i, row := range rows {
		posts[i] = PostPreviewDto{
			Id:      row.ID,
			Title:   row.Title,
			Summary: row.Summary,
			Date:    row.Date.Format(time.RFC3339),
			Visible: row.Visible,
		}
	}

	c.JSON(http.StatusOK, posts)
}

func (h *StorageHandler) GetPostsPublishedId(c *gin.Context, id string) {
	row, err := h.Queries.SelectVisiblePost(c.Request.Context(), id)
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	c.JSON(http.StatusOK, PostDto{
		Id:      row.ID,
		Title:   row.Title,
		Summary: row.Summary,
		Text:    row.Text,
		Date:    row.Date.Format(time.RFC3339),
		Visible: row.Visible,
	})
}

func (h *StorageHandler) GetRssPosts(c *gin.Context) {
	posts, err := h.Queries.SelectVisiblePosts(c.Request.Context())
	if err != nil {
		panic(err)
	}

	feed, err := helper.GenerateRss(posts)
	if err != nil {
		panic(err)
	}

	c.Data(http.StatusOK, "application/rss+xml", []byte(feed))
}

func (h *StorageHandler) GetPostsEditor(c *gin.Context) {
	rows, err := h.Queries.SelectAllPosts(c.Request.Context())
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	posts := make([]PostPreviewDto, len(rows))
	for i, row := range rows {
		posts[i] = PostPreviewDto{
			Id:      row.ID,
			Title:   row.Title,
			Summary: row.Summary,
			Date:    row.Date.Format(time.RFC3339),
			Visible: row.Visible,
		}
	}

	c.JSON(http.StatusOK, posts)
}

func (h *StorageHandler) PostPostsEditor(c *gin.Context) {
	var request PostPostsEditorJSONRequestBody
	if nil != c.Bind(&request) {
		c.Status(http.StatusBadRequest)
		return
	}

	if err := h.Queries.InsertPost(c.Request.Context(), db.InsertPostParams{
		ID:      uuid.NewString(),
		Title:   request.Title,
		Summary: request.Summary,
		Text:    request.Text,
		Date:    time.Now(),
		Visible: request.Visible,
	}); err != nil {
		panic(err)
	}

	c.Status(http.StatusCreated)
}

func (h *StorageHandler) GetPostsEditorId(c *gin.Context, id string) {
	row, err := h.Queries.SelectPost(c.Request.Context(), id)
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	c.JSON(http.StatusOK, PostDto{
		Id:      row.ID,
		Title:   row.Title,
		Summary: row.Summary,
		Text:    row.Text,
		Date:    row.Date.Format(time.RFC3339),
		Visible: row.Visible,
	})
}

func (h *StorageHandler) PutPostsEditorId(c *gin.Context, id string) {
	if _, err := h.Queries.SelectPost(c.Request.Context(), id); err != nil {
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
		fmt.Println("time", *request.Date, err)
		c.Status(http.StatusBadRequest)
		return
	}

	if err := h.Queries.UpdatePost(c.Request.Context(), db.UpdatePostParams{
		ID:      id,
		Title:   request.Title,
		Summary: request.Summary,
		Text:    request.Text,
		Date:    date,
		Visible: request.Visible,
	}); err != nil {
		panic(err)
	}

	c.Status(http.StatusOK)
}

func (h *StorageHandler) DeletePostsEditorId(c *gin.Context, id string) {
	if _, err := h.Queries.SelectPost(c.Request.Context(), id); err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	if err := h.Queries.DeletePost(c.Request.Context(), id); err != nil {
		panic(err)
	}

	c.Status(http.StatusOK)
}
