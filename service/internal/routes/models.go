package routes

type messageResponse struct {
	Message string `json:"message"`
}

type userRequest struct {
	Username string `json:"username" binding:"required"`
	Password string `json:"password" binding:"required"`
}

type createPostRequest struct {
	Title   string `json:"title" binding:"required"`
	Summary string `json:"summary" binding:"required"`
	Text    string `json:"text" binding:"required"`
	Visible bool   `json:"visible" binding:"required"`
}
