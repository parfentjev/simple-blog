package main

import (
	"github.com/gin-gonic/gin"
	"github.com/parfentjev/simple-blog/internal/db"
	"github.com/parfentjev/simple-blog/internal/routes"
)

func main() {
	db.InitDB()
	engine := gin.Default()
	routes.Register(engine)
	engine.Run()
}
