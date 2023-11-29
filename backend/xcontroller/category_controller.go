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

func GetCategories(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectDBHandler()
	defer db.Close()
	query := "SELECT * FROM categories"

	rows, err := db.Query(query)
	if err != nil {
		log.Println(err)
		xresponse.PrintError(http.StatusNotFound, "No Category Data Inserted To []Category", w)
		return
	}

	var category xmodel.Category
	var categories []xmodel.Category
	for rows.Next() {
		if err := rows.Scan(&category.ID, &category.Name, &category.Image); err != nil {
			xresponse.PrintError(http.StatusNotFound, "No Category Data Inserted To []Category", w)
			return
		}
		categories = append(categories, category)
	}

	var response xmodel.CategoriesResponse

	if len(categories) > 0 {
		response.Status = http.StatusOK
		response.Message = "Success"
		response.Data = categories
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(response)
	} else {
		xresponse.PrintError(http.StatusNotFound, "null []Category", w)
		return
	}
}

func AddCategory(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectGormDBHandler()

	err := r.ParseForm()
	xresponse.CheckError(err)

	name := r.Form.Get("name")
	image := r.Form.Get("image")

	var category xmodel.Category

	category.Name = name
	category.Image = image

	query := db.Select("name", "image").Create(&category)

	if query != nil {
		xresponse.PrintSuccess(http.StatusOK, "Category Inserted!", w)
	} else {
		xresponse.PrintError(http.StatusInternalServerError, "Insert Category Failed!", w)
	}
}

func DeleteCategory(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectGormDBHandler()

	idCategory := r.URL.Query().Get("id")

	var category xmodel.Category

	// check if category exists
	err := db.Where("id = ?", idCategory).First(&category).Error
	if err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			xresponse.PrintError(http.StatusNotFound, "Category Not Found", w)
		} else {
			xresponse.PrintError(http.StatusInternalServerError, "Server Error", w)
		}
		return
	}

	err = db.Delete(&category, idCategory).Error

	if err != nil {
		xresponse.PrintError(http.StatusInternalServerError, "Delete Category Failed", w)
		return
	}

	xresponse.PrintSuccess(http.StatusOK, "Category Deleted", w)
}

func GetCategory(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectDBHandler()
	defer db.Close()

	idCategory := r.URL.Query().Get("id")

	var category xmodel.Category
	query := "SELECT * FROM categories WHERE id = ?"

	err := db.QueryRow(query, idCategory).Scan(&category.ID, &category.Name, &category.Image)

	if err != nil {
		log.Println(err)
		xresponse.PrintError(http.StatusNotFound, "Category not found", w)
		return
	}

	var response xmodel.CategoryResponse
	response.Status = http.StatusOK
	response.Message = "Success"
	response.Data = category
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}

func UpdateCategory(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectGormDBHandler()

	err := r.ParseForm()
	xresponse.CheckError(err)

	idCategory := r.Form.Get("id")
	name := r.Form.Get("name")
	image := r.Form.Get("image")

	var category xmodel.Category
	category.ID, _ = strconv.Atoi(idCategory)

	query := db.First(&category, category.ID)
	if query.Error != nil {
		xresponse.PrintError(http.StatusNotFound, "Category Not Found", w)
		return
	}

	if name != "" {
		category.Name = name
	}

	if image != "" {
		category.Image = image
	}

	query = db.Save(&category)

	if query != nil {
		xresponse.PrintSuccess(http.StatusOK, "Category Updated", w)
	} else {
		xresponse.PrintError(http.StatusInternalServerError, "Update Category Failed", w)
	}
}
