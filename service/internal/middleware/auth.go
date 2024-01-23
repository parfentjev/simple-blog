package middleware

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/parfentjev/simple-blog/internal/utils"
)

func Authenticate(c *gin.Context) {
	token := c.Request.Header.Get("Authorization")
	if !utils.TokenValid(token) {
		c.AbortWithStatus(http.StatusUnauthorized)
	}

	c.Next()
}
