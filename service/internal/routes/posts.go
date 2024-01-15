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

func registerPostHandlers(e *gin.Engine, h *RequestHandler) {
	e.GET("/posts", h.getPosts)
	e.GET("/posts/:id", h.getPost)
	e.GET("/rss/posts", h.getRssFeed)

	auth := e.Group("/")
	auth.Use(middlewares.Authenticate)
	auth.POST("/posts", h.addPost)
	auth.PUT("/posts/:id", h.updatePost)
	auth.DELETE("/posts/:id", h.deletePost)
}

func (h *RequestHandler) getPosts(c *gin.Context) {
	posts, err := h.Queries.SelectPosts(c.Request.Context(), true)
	if err != nil {
		panic(err)
	}

	c.JSON(http.StatusOK, posts)
}

func (h *RequestHandler) addPost(c *gin.Context) {
	var request createPostRequest
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
		Visible: request.Visible,
	}); err != nil {
		panic(err)
	}

	c.Status(http.StatusCreated)
}

func (h *RequestHandler) getPost(c *gin.Context) {
	id := c.Param("id")
	post, err := h.Queries.SelectPostByVisible(c.Request.Context(), db.SelectPostByVisibleParams{ID: id, Visible: true})
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	c.JSON(http.StatusOK, post)
}

func (h *RequestHandler) updatePost(c *gin.Context) {
	id := c.Param("id")
	originalPost, err := h.Queries.SelectPost(c.Request.Context(), id)

	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	var request createPostRequest
	if err := c.ShouldBind(&request); err != nil {
		c.Status(http.StatusBadRequest)
		return
	}

	if err := h.Queries.UpdatePost(c.Request.Context(), db.UpdatePostParams{
		ID:      id,
		Title:   request.Title,
		Summary: request.Summary,
		Text:    request.Text,
		Date:    originalPost.Date,
		Visible: request.Visible,
	}); err != nil {
		panic(err)
	}

	c.Status(http.StatusOK)
}

func (h *RequestHandler) deletePost(c *gin.Context) {
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

func (h *RequestHandler) getRssFeed(c *gin.Context) {
	posts, err := h.Queries.SelectPosts(c.Request.Context(), true)
	if err != nil {
		panic(err)
	}

	feed, err := utils.GenerateRss(posts)
	if err != nil {
		panic(err)
	}

	c.Data(http.StatusOK, "application/rss+xml", []byte(feed))
}
