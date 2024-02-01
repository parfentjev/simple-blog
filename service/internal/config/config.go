package config

import (
	"fmt"

	"github.com/caarlos0/env/v10"
)

type app struct {
	PasswordSecret      string `env:"PASSWORD_SECRET,required"`
	JWTSecret           string `env:"JWT_SECRET,required"`
	RegistrationEnabled bool   `env:"REGISTRATION_ENABLED,required"`
}

var App = app{}

func Init() {
	if err := env.Parse(&App); err != nil {
		panic(fmt.Errorf("app config init: %w", err))
	}
}
