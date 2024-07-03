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

	config := config.NewConfig()
	engine := gin.Default()
	db.Init(db.Config{User: config.DbUser, Password: config.DbPassword, Database: config.DbName, Host: config.DbHost})

	engine.Use(
		cors.New(cors.Config{
			AllowOrigins:     config.AllowOrigins,
			AllowMethods:     []string{"GET", "POST", "PUT", "DELETE", "OPTIONS"},
			AllowHeaders:     []string{"Origin", "Content-Length", "Content-Type", "Authorization"},
			AllowCredentials: false,
			MaxAge:           12 * time.Hour,
		}),
		middleware.OapiRequestValidatorWithOptions(spec,
			&middleware.Options{
				Options: openapi3filter.Options{
					AuthenticationFunc: auth.NewAuthenticator(auth.NewTokenHandler(&config)),
				},
			}))

	server.RegisterHandlers(engine, server.NewRequestHandler(db.New(db.Connection), &config))
	engine.Run()
}
