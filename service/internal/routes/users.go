package routes

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/google/uuid"
	"github.com/parfentjev/simple-blog/internal/config"
	"github.com/parfentjev/simple-blog/internal/db"
	"github.com/parfentjev/simple-blog/internal/utils"
)

func registerUserHandlers(e *gin.Engine, h *RequestHandler) {
	e.POST("/users", h.createUser)
	e.POST("/users/token", h.createToken)
}

func (h *RequestHandler) createUser(c *gin.Context) {
	if !config.App.RegistrationEnabled {
		c.JSON(http.StatusForbidden, messageResponse{"User creation is disabled."})
		return
	}

	var request addUserRequest
	if nil != c.ShouldBindJSON(&request) {
		c.Status(http.StatusBadRequest)
		return
	}

	hashedPassword, err := utils.HashPassword(request.Password)
	if err != nil {
		panic(err)
	}

	err = h.Queries.InsertUser(c.Request.Context(), db.InsertUserParams{
		ID:       uuid.NewString(),
		Username: request.Username,
		Password: hashedPassword,
		Active:   true,
	})
	if err != nil {
		panic(err)
	}

	c.Status(http.StatusCreated)
}

func (h *RequestHandler) createToken(c *gin.Context) {
	var request addUserRequest
	if nil != c.ShouldBindJSON(&request) {
		c.Status(http.StatusBadRequest)
		return
	}

	selectedUser, err := h.Queries.SelectUser(c.Request.Context(), request.Username)
	if err != nil || !selectedUser.Active || !utils.PasswordValid(selectedUser.Password, request.Password) {
		c.Status(http.StatusUnauthorized)
		return
	}

	token, err := utils.GenerateToken(selectedUser.ID)
	if err != nil {
		panic(err)
	}

	c.JSON(http.StatusOK, token)
}
