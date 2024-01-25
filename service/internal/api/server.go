package api

import (
	"context"
	"fmt"

	"github.com/getkin/kin-openapi/openapi3filter"
	"github.com/parfentjev/simple-blog/internal/user"
)

func NewAuthenticator() openapi3filter.AuthenticationFunc {
	return func(ctx context.Context, input *openapi3filter.AuthenticationInput) error {
		return Authenticate(ctx, input)
	}
}

func Authenticate(ctx context.Context, input *openapi3filter.AuthenticationInput) error {
	token := input.RequestValidationInput.Request.Header.Get("Authorization")
	if !user.TokenValid(token) {
		return fmt.Errorf("token is not valid")
	}

	return nil
}
