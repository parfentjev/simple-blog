package main

import (
	"time"

	"github.com/getkin/kin-openapi/openapi3filter"
	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	middleware "github.com/oapi-codegen/gin-middleware"
	"github.com/parfentjev/simple-blog/internal/config"
	"github.com/parfentjev/simple-blog/internal/db"
	"github.com/parfentjev/simple-blog/internal/server"
	"github.com/parfentjev/simple-blog/internal/server/auth"
)

func main() {
	spec, err := server.GetSwagger()
	if err != nil {
		panic(err)
	}

	db.Init()
	config.Init()
	engine := gin.Default()

	engine.Use(
		cors.New(cors.Config{
			AllowOrigins:     []string{"https://fakeplastictrees.ee", "https://www.fakeplastictrees.ee", "http://localhost:3000"},
			AllowMethods:     []string{"GET", "POST", "PUT", "DELETE", "OPTIONS"},
			AllowHeaders:     []string{"Origin", "Content-Length", "Content-Type", "Authorization"},
			AllowCredentials: false,
			MaxAge:           12 * time.Hour,
		}),
		middleware.OapiRequestValidatorWithOptions(spec,
			&middleware.Options{
				Options: openapi3filter.Options{
					AuthenticationFunc: auth.NewAuthenticator(auth.NewTokenHandler()),
				},
			}))

	server.RegisterHandlers(engine, server.NewStorageHandler(db.New(db.Connection)))
	engine.Run()
}
