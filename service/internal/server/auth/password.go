package auth

import (
	"github.com/parfentjev/simple-blog/internal/config"
	"golang.org/x/crypto/bcrypt"
)

type PasswordAuth struct {
	secret string
}

func NewPasswordAuth(config *config.Config) *PasswordAuth {
	return &PasswordAuth{config.PasswordSecret}
}

func (p *PasswordAuth) HashPassword(password string) (string, error) {
	result, err := bcrypt.GenerateFromPassword([]byte(password+p.secret), 10)

	return string(result), err
}

func (p *PasswordAuth) PasswordValid(hashedPassword string, password string) bool {
	return nil == bcrypt.CompareHashAndPassword([]byte(hashedPassword), []byte(password+p.secret))
}
