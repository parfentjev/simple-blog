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
		AllowOrigins: []string{"https://fakeplastictrees.ee", "http://fakeplastictrees.ee", "http://localhost:3000"},
		MaxAge:       12 * time.Hour,
	}))
	routes.Register(engine)
	engine.Run()
}
