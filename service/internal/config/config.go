package config

import (
	"fmt"

	"github.com/caarlos0/env/v10"
)

type app struct {
	PasswordSecret      string   `env:"PASSWORD_SECRET,required"`
	JWTSecret           string   `env:"JWT_SECRET,required"`
	RegistrationEnabled bool     `env:"REGISTRATION_ENABLED,required"`
	RssFeedTitle        string   `env:"RSS_FEED_TITLE,required"`
	RssFeedDescription  string   `env:"RSS_FEED_DESCRIPTION,required"`
	RssFeedBaseUrl      string   `env:"RSS_FEED_BASE_URL,required"`
	AllowOrigins        []string `env:"ALLOW_ORIGINS,required"`
}

var App = app{}

func Init() {
	if err := env.Parse(&App); err != nil {
		panic(fmt.Errorf("app config init: %w", err))
	}
}
