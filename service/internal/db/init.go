package db

import (
	"database/sql"
	_ "embed"

	_ "github.com/mattn/go-sqlite3"
)

var Connection *sql.DB

func Init() {
	var err error

	Connection, err = sql.Open("sqlite3", "data/server.db")
	if err != nil {
		panic(err)
	}

	Connection.SetMaxOpenConns(10)
	Connection.SetMaxIdleConns(5)
}
