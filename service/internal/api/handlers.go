package routes

import (
	"github.com/gin-gonic/gin"
	"github.com/parfentjev/simple-blog/internal/db"
)

func Register(e *gin.Engine) {
	h := NewStorageHandler(db.New(db.Connection))
	registerPostHandlers(e, h)
	registerUserHandlers(e, h)
}

type StorageHandler struct {
	Queries *db.Queries
}

func NewStorageHandler(queries *db.Queries) *StorageHandler {
	return &StorageHandler{queries}
}
