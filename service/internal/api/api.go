package api

import (
	"github.com/parfentjev/simple-blog/internal/db"
)

type StorageHandler struct {
	Queries *db.Queries
}

func NewStorageHandler(queries *db.Queries) *StorageHandler {
	return &StorageHandler{queries}
}

type MessageResponse struct {
	Message string `json:"message"`
}

func AuthenticationFunc() {

}
