package routes

import (
	"github.com/gin-gonic/gin"
	"github.com/parfentjev/simple-blog/internal/db"
)

func Register(e *gin.Engine) {
	h := NewRequestHandler(db.New(db.Connection))
	registerPostHandlers(e, h)
}

type RequestHandler struct {
	Queries *db.Queries
}

func NewRequestHandler(queries *db.Queries) *RequestHandler {
	return &RequestHandler{queries}
}
