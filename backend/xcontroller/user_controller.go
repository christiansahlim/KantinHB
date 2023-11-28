package xcontroller

import (
	"KantinHB/xhandler"
	"KantinHB/xmodel"
	"KantinHB/xresponse"
	"encoding/json"
	"errors"
	"gorm.io/gorm"
	"log"
	"net/http"
	"strconv"
)

func GetUsers(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectDBHandler()
	defer db.Close()
	query := "SELECT * FROM users"

	rows, err := db.Query(query)
	if err != nil {
		log.Println(err)
		xresponse.PrintError(http.StatusNotFound, "No User Data Inserted To []User", w)
		return
	}

	var user xmodel.User
	var users []xmodel.User
	for rows.Next() {
		if err := rows.Scan(&user.ID, &user.Name, &user.Email, &user.Password, &user.Admin); err != nil {
			xresponse.PrintError(http.StatusNotFound, "No User Data Inserted To []User", w)
			return
		}
		users = append(users, user)
	}

	var response xmodel.UsersResponse

	if len(users) > 0 {
		response.Status = http.StatusOK
		response.Message = "Success"
		response.Data = users
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(response)
	} else {
		xresponse.PrintError(http.StatusNotFound, "null []User", w)
		return
	}
}

func AddUser(w http.ResponseWriter, r *http.Request) {

	db := xhandler.ConnectGormDBHandler()

	err := r.ParseForm()
	xresponse.CheckError(err)

	name := r.Form.Get("name")
	email := r.Form.Get("email")
	password := r.Form.Get("password")

	var user xmodel.User

	user.Name = name
	user.Email = email
	user.Password = password
	user.Admin = false

	query := db.Select("name", "email", "password", "admin").Create(&user)

	if query != nil {
		xresponse.PrintSuccess(http.StatusOK, "User Inserted", w)
	} else {
		xresponse.PrintError(http.StatusInternalServerError, "Insert User Failed", w)
	}
}

func DeleteUser(w http.ResponseWriter, r *http.Request) {

	db := xhandler.ConnectGormDBHandler()

	idUser := r.URL.Query().Get("id")

	var user xmodel.User

	// check if user exists
	err := db.Where("id = ?", idUser).First(&user).Error
	if err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			xresponse.PrintError(http.StatusNotFound, "User Not Found", w)
		} else {
			xresponse.PrintError(http.StatusInternalServerError, "Server Error", w)
		}
		return
	}

	err = db.Delete(&user, idUser).Error

	if err != nil {
		xresponse.PrintError(http.StatusInternalServerError, "Delete User Failed", w)
		return
	}

	xresponse.PrintSuccess(http.StatusOK, "User Deleted", w)
}

func GetUser(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectDBHandler()
	defer db.Close()

	idUser := r.URL.Query().Get("id")

	var user xmodel.User
	query := "SELECT * FROM users WHERE id = ?"

	err := db.QueryRow(query, idUser).Scan(&user.ID, &user.Name, &user.Email, &user.Password, &user.Admin)

	if err != nil {
		log.Println(err)
		xresponse.PrintError(http.StatusNotFound, "User not found", w)
		return
	}

	var response xmodel.UserResponse
	response.Status = http.StatusOK
	response.Message = "Success"
	response.Data = user
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}

func UpdateUser(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectGormDBHandler()

	err := r.ParseForm()
	xresponse.CheckError(err)

	userID := r.Form.Get("id")
	name := r.Form.Get("name")
	email := r.Form.Get("email")
	password := r.Form.Get("password")

	var user xmodel.User
	user.ID, _ = strconv.Atoi(userID)

	query := db.First(&user, user.ID)
	if query.Error != nil {
		xresponse.PrintError(http.StatusNotFound, "User Not Found", w)
		return
	}

	if name != "" {
		user.Name = name
	}

	if email != "" {
		user.Email = email
	}

	if password != "" {
		user.Password = password
	}

	query = db.Save(&user)

	if query != nil {
		xresponse.PrintSuccess(http.StatusOK, "User Updated", w)
	} else {
		xresponse.PrintError(http.StatusInternalServerError, "Update User Failed", w)
	}
}

func UpdateUserEdit(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectGormDBHandler()

	err := r.ParseForm()
	xresponse.CheckError(err)

	name := r.Form.Get("name")
	email := r.Form.Get("email")
	password := r.Form.Get("password")

	var user xmodel.User
	user.ID = xhandler.GetOnlineUserId(r)

	query := db.First(&user, user.ID)
	if query.Error != nil {
		xresponse.PrintError(http.StatusNotFound, "User Not Found", w)
		return
	}

	if name != "" {
		user.Name = name
	}

	if email != "" {
		user.Email = email
	}

	if password != "" {
		user.Password = password
	}

	query = db.Save(&user)

	if query != nil {
		xresponse.PrintSuccess(http.StatusOK, "User Updated", w)
	} else {
		xresponse.PrintError(http.StatusInternalServerError, "Update User Failed", w)
	}
}
