package server

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/google/uuid"
	"github.com/parfentjev/simple-blog/internal/config"
	"github.com/parfentjev/simple-blog/internal/db"
	"github.com/parfentjev/simple-blog/internal/server/auth"
)

func (s *StorageHandler) PostUsers(c *gin.Context) {
	if !config.App.RegistrationEnabled {
		c.JSON(http.StatusForbidden, MessageResponse{"User creation is disabled."})
		return
	}

	var request PostUsersJSONBody
	if nil != c.Bind(&request) {
		c.Status(http.StatusBadRequest)
		return
	}

	passAuth := auth.NewPasswordAuth()
	hashedPassword, err := passAuth.HashPassword(request.Password)
	if err != nil {
		panic(err)
	}

	err = s.Queries.InsertUser(c.Request.Context(), db.InsertUserParams{
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

func (s *StorageHandler) PostUsersToken(c *gin.Context) {
	var request PostUsersTokenJSONBody
	if nil != c.Bind(&request) {
		c.Status(http.StatusBadRequest)
		return
	}

	passAuth := auth.NewPasswordAuth()
	selectedUser, err := s.Queries.SelectUser(c.Request.Context(), request.Username)
	if err != nil || !selectedUser.Active || !passAuth.PasswordValid(selectedUser.Password, request.Password) {
		c.Status(http.StatusUnauthorized)
		return
	}

	token, err := auth.NewTokenHandler().CreateToken(selectedUser.ID)
	if err != nil {
		panic(err)
	}

	c.JSON(http.StatusOK, UserTokenDto{Token: token.Token, Expires: token.ExpirationDate})
}
