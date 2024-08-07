package config

import (
	"fmt"

	"github.com/caarlos0/env/v10"
)

type Config struct {
	PasswordSecret      string   `env:"PASSWORD_SECRET,required"`
	JWTSecret           string   `env:"JWT_SECRET,required"`
	RegistrationEnabled bool     `env:"REGISTRATION_ENABLED,required"`
	RssFeedTitle        string   `env:"RSS_FEED_TITLE,required"`
	RssFeedDescription  string   `env:"RSS_FEED_DESCRIPTION,required"`
	RssFeedBaseUrl      string   `env:"RSS_FEED_BASE_URL,required"`
	AllowOrigins        []string `env:"ALLOW_ORIGINS,required"`
	ItemLimit           int64    `env:"ITEM_LIMIT,required"`
	DbUser              string   `env:"DB_USER,required"`
	DbPassword          string   `env:"DB_PASSWORD,required"`
	DbName              string   `env:"DB_NAME,required"`
	DbHost              string   `env:"DB_HOST,required"`
}

func NewConfig() Config {
	var config = Config{}

	if err := env.Parse(&config); err != nil {
		panic(fmt.Errorf("app config init: %w", err))
	}

	return config
}
