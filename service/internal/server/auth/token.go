package auth

import (
	"fmt"
	"time"

	"github.com/golang-jwt/jwt/v5"
	"github.com/parfentjev/simple-blog/internal/config"
)

type TokenHandler struct {
	signingMethod *jwt.SigningMethodHMAC
}

type Token struct {
	Token          string
	ExpirationDate int
}

func NewTokenHandler() *TokenHandler {
	return &TokenHandler{
		jwt.SigningMethodHS256,
	}
}

func (h *TokenHandler) ValidateToken(token string) error {
	parsedToken, err := jwt.Parse(token, func(token *jwt.Token) (any, error) {
		_, ok := token.Method.(*jwt.SigningMethodHMAC)
		if !ok {
			return nil, fmt.Errorf("signing method is not valid")
		}

		return []byte(config.App.JWTSecret), nil
	})

	if err != nil {
		return fmt.Errorf("token validation: %w", err)
	}

	if !parsedToken.Valid {
		return fmt.Errorf("token validation: not valid")
	}

	claims, ok := parsedToken.Claims.(jwt.MapClaims)
	if !ok {
		return fmt.Errorf("token validation: couldn't parse claims")
	}

	if int64(claims["exp"].(float64)) < time.Now().Unix() {
		return fmt.Errorf("token validation: expired")
	}

	return nil
}

func (h *TokenHandler) CreateToken(userId string) (Token, error) {
	exp := time.Now().Add(time.Hour * 24 * 7).Unix()
	signedToken, err := jwt.NewWithClaims(h.signingMethod, jwt.MapClaims{
		"sub": userId,
		"exp": exp,
	}).SignedString([]byte(config.App.JWTSecret))

	if err != nil {
		return Token{}, fmt.Errorf("create token: %w", err)
	}

	return Token{signedToken, int(exp)}, nil
}
