package server

import (
	"github.com/parfentjev/simple-blog/internal/config"
	"github.com/parfentjev/simple-blog/internal/db"
)

type RequestHandler struct {
	queries *db.Queries
	config  *config.Config
}

func NewRequestHandler(queries *db.Queries, config *config.Config) *RequestHandler {
	return &RequestHandler{queries, config}
}

type MessageResponse struct {
	Message string `json:"message"`
}
