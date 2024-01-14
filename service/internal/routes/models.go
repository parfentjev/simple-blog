package routes

type messageResponse struct {
	Message string `json:"message"`
}

type userRequest struct {
	Username string `json:"username" binding:"required"`
	Password string `json:"password" binding:"required"`
}

type tokenResponse struct {
	Token string `json:"token"`
}
