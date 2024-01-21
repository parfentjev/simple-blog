package routes

import "time"

type messageResponse struct {
	Message string `json:"message"`
}

type postUsersRequest struct {
	Username string `json:"username" binding:"required"`
	Password string `json:"password" binding:"required"`
}

type tokenDto struct {
	Token          string `json:"token"`
	ExpirationDate int64  `json:"expirationDate"`
}

type postPostsRequest struct {
	Title   string `json:"title" binding:"required"`
	Summary string `json:"summary" binding:"required"`
	Text    string `json:"text" binding:"required"`
	Visible *bool  `json:"visible" binding:"required"`
}

type putPostsRequest struct {
	Id      string    `json:"id"`
	Title   string    `json:"title" binding:"required"`
	Summary string    `json:"summary" binding:"required"`
	Text    string    `json:"text" binding:"required"`
	Date    time.Time `json:"date" binding:"required"`
	Visible *bool     `json:"visible" binding:"required"`
}
