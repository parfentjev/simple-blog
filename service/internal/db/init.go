package db

import (
	"database/sql"
	_ "embed"
	"fmt"

	_ "github.com/lib/pq"
)

var Connection *sql.DB

type Config struct {
	User     string
	Password string
	Database string
	Host     string
}

func Init(c Config) {
	var err error

	connStr := fmt.Sprintf("user=%v password=%v dbname=%v host=%v sslmode=disable", c.User, c.Password, c.Database, c.Host)
	Connection, err = sql.Open("postgres", connStr)
	if err != nil {
		panic(err)
	}

	Connection.SetMaxOpenConns(10)
	Connection.SetMaxIdleConns(5)
}
