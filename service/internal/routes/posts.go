package routes

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/parfentjev/simple-blog/internal/db"
	"github.com/parfentjev/simple-blog/internal/rss"
)

func registerPostHandlers(e *gin.Engine, h *RequestHandler) {
	e.GET("/posts", h.getPosts)
	e.POST("/posts", h.addPost)
	e.GET("/posts/:id", h.getPost)
	e.PUT("/posts/:id", h.updatePost)
	e.DELETE("/posts/:id", h.deletePost)
	e.GET("/rss/posts", h.getRssFeed)
}

func (h *RequestHandler) getPosts(c *gin.Context) {
	posts, err := h.Queries.GetPosts(c.Request.Context(), true)
	if err != nil {
		panic(err)
	}

	c.JSON(http.StatusOK, posts)
}

func (h *RequestHandler) addPost(c *gin.Context) {
	c.Status(http.StatusUnauthorized)
}

func (h *RequestHandler) getPost(c *gin.Context) {
	id := c.Param("id")
	post, err := h.Queries.GetPost(c.Request.Context(), db.GetPostParams{ID: id, Visible: true})
	if err != nil {
		c.Status(http.StatusNotFound)
		return
	}

	c.JSON(http.StatusOK, post)
}

func (h *RequestHandler) updatePost(c *gin.Context) {
	c.Status(http.StatusUnauthorized)
}

func (h *RequestHandler) deletePost(c *gin.Context) {
	c.Status(http.StatusUnauthorized)
}

func (h *RequestHandler) getRssFeed(c *gin.Context) {
	posts, err := h.Queries.GetPosts(c.Request.Context(), true)
	if err != nil {
		panic(err)
	}

	feed, err := rss.Generate(posts)
	if err != nil {
		panic(err)
	}

	c.Data(http.StatusOK, "application/rss+xml", []byte(feed))
}
