package utils

import (
	"errors"
	"log"
	"strings"
	"time"

	"github.com/golang-jwt/jwt/v5"
	"github.com/parfentjev/simple-blog/internal/config"
	"golang.org/x/crypto/bcrypt"
)

func HashPassword(password string) (string, error) {
	result, err := bcrypt.GenerateFromPassword([]byte(password), 10)

	return string(result), err
}

func PasswordValid(hashedPassword string, password string) bool {
	return nil == bcrypt.CompareHashAndPassword([]byte(hashedPassword), []byte(password))
}

func GenerateToken(sub string) (string, error) {
	return jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"sub": sub,
		"exp": time.Now().Add(time.Hour * 24 * 7).Unix(),
	}).SignedString([]byte(config.App.HashSecret))
}

func TokenValid(token string) bool {
	splitToken := strings.Split(token, " ")
	if len(splitToken) != 2 {
		return false
	}

	parsedToken, err := jwt.Parse(splitToken[1], func(token *jwt.Token) (any, error) {
		_, ok := token.Method.(*jwt.SigningMethodHMAC)
		if !ok {
			return nil, errors.New("signing method is not valid")
		}

		return []byte(config.App.HashSecret), nil
	})

	if err != nil {
		log.Println(err)
		return false
	}

	if !parsedToken.Valid {
		return false
	}

	claims, ok := parsedToken.Claims.(jwt.MapClaims)
	if !ok {
		return false
	}

	return int64(claims["exp"].(float64)) > time.Now().Unix()
}
