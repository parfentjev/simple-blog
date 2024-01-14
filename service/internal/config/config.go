package config

import "github.com/caarlos0/env/v10"

type app struct {
	HashSecret          string `env:"HASH_SECRET,required"`
	RegistrationEnabled bool   `env:"REGISTRATION_ENABLED,required"`
}

var App = app{}

func Init() {
	if err := env.Parse(&App); err != nil {
		panic(err)
	}
}
