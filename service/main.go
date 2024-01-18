package main

import (
	"time"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"github.com/parfentjev/simple-blog/internal/config"
	"github.com/parfentjev/simple-blog/internal/db"
	"github.com/parfentjev/simple-blog/internal/routes"
)

func main() {
	db.Init()
	config.Init()
	engine := gin.Default()
	engine.Use(cors.New(cors.Config{
		AllowOrigins: []string{"https://fakeplastictrees.ee", "https://www.fakeplastictrees.ee", "http://localhost:3000"},
		AllowMethods: []string{"GET", "POST", "PUT", "DELETE"},
		AllowHeaders: []string{"content-type", "authorization"},
		MaxAge:       12 * time.Hour,
	}))
	routes.Register(engine)
	engine.Run()
}
