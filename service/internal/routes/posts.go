package routes

import (
	"net/http"

	"github.com/gin-gonic/gin"
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
	c.Status(http.StatusOK)
}

func (h *RequestHandler) getPost(c *gin.Context) {
	id := c.Param("id")
	post, err := h.Queries.SelectPost(c.Request.Context(), db.SelectPostParams{ID: id, Visible: true})
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	c.JSON(http.StatusOK, post)
}

func (h *RequestHandler) updatePost(c *gin.Context) {
	c.Status(http.StatusOK)
}

func (h *RequestHandler) deletePost(c *gin.Context) {
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
