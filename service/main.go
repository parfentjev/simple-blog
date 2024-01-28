package main

import (
	"github.com/getkin/kin-openapi/openapi3filter"
	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	middleware "github.com/oapi-codegen/gin-middleware"
	"github.com/parfentjev/simple-blog/internal/api"
	"github.com/parfentjev/simple-blog/internal/config"
	"github.com/parfentjev/simple-blog/internal/db"
)

func main() {
	spec, err := api.GetSwagger()
	if err != nil {
		panic(err)
	}

	db.Init()
	config.Init()
	engine := gin.Default()

	corsConfig := cors.DefaultConfig()
	corsConfig.AllowOrigins = []string{"https://fakeplastictrees.ee", "https://www.fakeplastictrees.ee", "http://localhost:3000"}

	engine.Use(middleware.OapiRequestValidatorWithOptions(spec,
		&middleware.Options{
			Options: openapi3filter.Options{
				AuthenticationFunc: api.NewAuthenticator(),
			},
		}),
		cors.New(corsConfig))

	api.RegisterHandlers(engine, api.NewStorageHandler(db.New(db.Connection)))
	engine.Run()
}
