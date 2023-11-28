package xhandler

import (
	"KantinHB/xresponse"
	"database/sql"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"log"
	"os"
)

func ConnectDBHandler() *sql.DB {
	db, err := sql.Open("mysql", os.Getenv("DB_URL"))
	xresponse.CheckError(err)
	return db
}

func ConnectGormDBHandler() *gorm.DB {
	db, err := gorm.Open(mysql.Open(os.Getenv("DB_URL")), &gorm.Config{})

	if err != nil {
		log.Fatal(err)
	}
	return db
}
