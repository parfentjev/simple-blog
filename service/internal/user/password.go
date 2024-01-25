package user

import "golang.org/x/crypto/bcrypt"

func HashPassword(password string) (string, error) {
	result, err := bcrypt.GenerateFromPassword([]byte(password), 10)

	return string(result), err
}

func PasswordValid(hashedPassword string, password string) bool {
	return nil == bcrypt.CompareHashAndPassword([]byte(hashedPassword), []byte(password))
}
