package xcontroller

import (
	"KantinHB/xhandler"
	"KantinHB/xmodel"
	"KantinHB/xresponse"
	"encoding/json"
	"context"
	"github.com/go-redis/redis/v8"
	"net/http"
	"time"
	"log"
)

// UserRegister Register
func UserRegister(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectDBHandler()
	defer db.Close()

	err := r.ParseForm()
	xresponse.CheckError(err)

	Name := r.Form.Get("name")
	Email := r.Form.Get("email")
	Password := r.Form.Get("password")

	// Check if user already exists
	row := db.QueryRow("SELECT id FROM users WHERE email = ?", Email)
	var id int
	err = row.Scan(&id)
	if err == nil {
		xresponse.PrintError(400, "User already exists", w)
		return
	}

	// If user does not exist, register them
	_, errQuery := db.Exec("INSERT INTO users(Name, Email, Password, Admin) VALUES(?, ?, ?, 0)",
		Name,
		Email,
		Password)

	if errQuery == nil {
		xresponse.PrintSuccess(200, "Registered", w)
	} else {
		xresponse.PrintError(400, "Registration Failed", w)
		return
	}
}

func UserLogin(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectDBHandler()
	defer db.Close()

	errx := r.ParseForm()
	if errx != nil {
		xresponse.PrintError(400, "Server Error", w)
		return
	}

	email := r.Form.Get("email")
	password := r.Form.Get("password")

	log.Println("Email    : " + email)
	log.Println("Password : " + password)

	row := db.QueryRow("SELECT * FROM users WHERE email = ? AND password = ?", email, password)

	var user xmodel.User
	err := row.Scan(&user.ID, &user.Name, &user.Email, &user.Password, &user.Admin)

	if err != nil {
		xresponse.PrintError(400, "User Not Found", w)
	} else {
		xhandler.GenerateToken(w, user.ID, user.Email, user.Admin)
		level := "User"
		if user.Admin {
			level = "Admin"
		} else {
			//StartScheduler(user)
		}

		var response xmodel.LoginResponse
		response.Status = http.StatusOK
		response.Message = "Logged In as " + level
		response.Data = user
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(response)
	}
}

func Logout(w http.ResponseWriter, r *http.Request) {
	xhandler.ResetToken(w)
	xresponse.PrintSuccess(200, "Logged Out", w)
	StopScheduler()
}

var ctx = context.Background()

func GetRedisClient() *redis.Client {
	return redis.NewClient(&redis.Options{
		Addr:     "localhost:6379",
		Password: "", // no password set
		DB:       0}) // use default DB
}

func SetRedis(rdb *redis.Client, key string, value string, expiration time.Duration) {
	err := rdb.Set(ctx, key, value, expiration).Err()
	xresponse.CheckError(err)
}

func GetRedis(rdb *redis.Client, key string) string {
	val, err := rdb.Get(ctx, key).Result()
	xresponse.CheckError(err)
	return val
}

func IsRedisKeyExists(rdb *redis.Client, key string) bool {
	exists, err := rdb.Exists(ctx, key).Result()
	xresponse.CheckError(err)
	return exists == 1
}

func ClearRedisCache(rdb *redis.Client) error {
	_, err := rdb.FlushAll(ctx).Result()
	return err
}
