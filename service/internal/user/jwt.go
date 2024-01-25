package user

import (
	"fmt"
	"log"
	"strings"
	"time"

	"github.com/golang-jwt/jwt/v5"
	"github.com/parfentjev/simple-blog/internal/config"
)

type Token struct {
	Token          string
	ExpirationDate int
}

func GenerateToken(sub string) (Token, error) {
	exp := time.Now().Add(time.Hour * 24 * 7).Unix()
	signedToken, err := jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"sub": sub,
		"exp": exp,
	}).SignedString([]byte(config.App.HashSecret))

	if err != nil {
		return Token{}, err
	}

	return Token{signedToken, int(exp)}, nil
}

func TokenValid(token string) bool {
	splitToken := strings.Split(token, " ")
	if len(splitToken) != 2 {
		return false
	}

	parsedToken, err := jwt.Parse(splitToken[1], func(token *jwt.Token) (any, error) {
		_, ok := token.Method.(*jwt.SigningMethodHMAC)
		if !ok {
			return nil, fmt.Errorf("signing method is not valid")
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
