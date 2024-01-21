package routes

import (
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/google/uuid"
	"github.com/parfentjev/simple-blog/internal/db"
	"github.com/parfentjev/simple-blog/internal/middlewares"
	"github.com/parfentjev/simple-blog/internal/utils"
)

func registerPostHandlers(public *gin.Engine, h *StorageHandler) {
	public.GET("/posts/published", h.getPostsPublished)
	public.GET("/posts/published/:id", h.getPostPublishedById)
	public.GET("/rss/posts", h.getRssPosts)

	editor := public.Group("/")
	editor.Use(middlewares.Authenticate)
	editor.POST("/posts/editor", h.postPostsEditorById)
	editor.GET("/posts/editor/:id", h.getPostsEditor)
	editor.PUT("/posts/editor/:id", h.putPostsEditorById)
	editor.DELETE("/posts/editor/:id", h.deletePostsEditorById)
}

func (h *StorageHandler) getPostsPublished(c *gin.Context) {
	posts, err := h.Queries.SelectVisiblePosts(c.Request.Context())
	if err != nil {
		panic(err)
	}

	c.JSON(http.StatusOK, posts)
}

func (h *StorageHandler) postPostsEditorById(c *gin.Context) {
	var request postPostsRequest
	if err := c.ShouldBind(&request); err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	if err := h.Queries.InsertPost(c.Request.Context(), db.InsertPostParams{
		ID:      uuid.NewString(),
		Title:   request.Title,
		Summary: request.Summary,
		Text:    request.Text,
		Date:    time.Now(),
		Visible: *request.Visible,
	}); err != nil {
		panic(err)
	}

	c.Status(http.StatusCreated)
}

func (h *StorageHandler) getPostsEditor(c *gin.Context) {
	id := c.Param("id")

	var (
		post db.Post
		err  error
	)

	post, err = h.Queries.SelectPost(c.Request.Context(), id)
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	c.JSON(http.StatusOK, post)
}

func (h *StorageHandler) getPostPublishedById(c *gin.Context) {
	id := c.Param("id")

	var (
		post db.Post
		err  error
	)

	post, err = h.Queries.SelectVisiblePost(c.Request.Context(), id)
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	c.JSON(http.StatusOK, post)
}

func (h *StorageHandler) putPostsEditorById(c *gin.Context) {
	id := c.Param("id")
	if _, err := h.Queries.SelectPost(c.Request.Context(), id); err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	var request putPostsRequest
	if err := c.ShouldBind(&request); err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	if err := h.Queries.UpdatePost(c.Request.Context(), db.UpdatePostParams{
		ID:      id,
		Title:   request.Title,
		Summary: request.Summary,
		Text:    request.Text,
		Date:    request.Date,
		Visible: *request.Visible,
	}); err != nil {
		panic(err)
	}

	c.Status(http.StatusOK)
}

func (h *StorageHandler) deletePostsEditorById(c *gin.Context) {
	id := c.Param("id")
	if _, err := h.Queries.SelectPost(c.Request.Context(), id); err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	if err := h.Queries.DeletePost(c.Request.Context(), id); err != nil {
		panic(err)
	}

	c.Status(http.StatusOK)
}

func (h *StorageHandler) getRssPosts(c *gin.Context) {
	posts, err := h.Queries.SelectVisiblePosts(c.Request.Context())
	if err != nil {
		panic(err)
	}

	feed, err := utils.GenerateRss(posts)
	if err != nil {
		panic(err)
	}

	c.Data(http.StatusOK, "application/rss+xml", []byte(feed))
}
