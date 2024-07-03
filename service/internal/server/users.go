package server

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/parfentjev/simple-blog/internal/db"
	"github.com/parfentjev/simple-blog/internal/server/auth"
)

func (h *RequestHandler) PostUsers(c *gin.Context) {
	if !h.config.RegistrationEnabled {
		c.JSON(http.StatusForbidden, MessageResponse{"user creation is disabled"})
		return
	}

	var request PostUsersJSONBody
	if nil != c.Bind(&request) {
		c.Status(http.StatusBadRequest)
		return
	}

	passAuth := auth.NewPasswordAuth(h.config)
	hashedPassword, err := passAuth.HashPassword(request.Password)
	if err != nil {
		panic(err)
	}

	err = h.queries.InsertUser(c.Request.Context(), db.InsertUserParams{
		Username: request.Username,
		Password: hashedPassword,
	})
	if err != nil {
		panic(err)
	}

	c.Status(http.StatusCreated)
}

func (h *RequestHandler) PostUsersToken(c *gin.Context) {
	var request PostUsersTokenJSONBody
	if nil != c.Bind(&request) {
		c.Status(http.StatusBadRequest)
		return
	}

	passAuth := auth.NewPasswordAuth(h.config)
	selectedUser, err := h.queries.SelectUser(c.Request.Context(), request.Username)
	if err != nil || !passAuth.PasswordValid(selectedUser.Password, request.Password) {
		c.Status(http.StatusUnauthorized)
		return
	}

	token, err := auth.NewTokenHandler(h.config).CreateToken(selectedUser.ID.String())
	if err != nil {
		panic(err)
	}

	c.JSON(http.StatusOK, UserTokenDto{Token: token.Token, Expires: token.ExpirationDate})
}
