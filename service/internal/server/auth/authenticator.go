package auth

import (
	"context"
	"fmt"
	"net/http"
	"strings"

	"github.com/getkin/kin-openapi/openapi3filter"
)

type TokenValidator interface {
	ValidateToken(token string) error
}

func NewAuthenticator(v TokenValidator) openapi3filter.AuthenticationFunc {
	return func(ctx context.Context, input *openapi3filter.AuthenticationInput) error {
		return Authenticate(v, ctx, input)
	}
}

func Authenticate(v TokenValidator, ctx context.Context, input *openapi3filter.AuthenticationInput) error {
	if input.SecuritySchemeName != "bearerAuth" {
		return fmt.Errorf("security scheme %s != 'bearerAuth'", input.SecuritySchemeName)
	}

	jws, err := GetTokenFromRequest(input.RequestValidationInput.Request)
	if err != nil {
		return fmt.Errorf("getting token: %w", err)
	}

	if v.ValidateToken(jws) != nil {
		return fmt.Errorf("validating token: %w", err)
	}

	return nil
}

func GetTokenFromRequest(req *http.Request) (string, error) {
	authHdr := req.Header.Get("Authorization")
	if authHdr == "" {
		return "", fmt.Errorf("authorization header is missing")
	}

	prefix := "Bearer "
	if !strings.HasPrefix(authHdr, prefix) {
		return "", fmt.Errorf("authorization header is malformed")
	}

	return strings.TrimPrefix(authHdr, prefix), nil
}
