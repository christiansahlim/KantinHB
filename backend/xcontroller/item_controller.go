package xcontroller

import (
	"KantinHB/xhandler"
	"KantinHB/xmodel"
	"KantinHB/xresponse"
	"encoding/json"
	"errors"
	"fmt"
	"gorm.io/gorm"
	"log"
	"net/http"
	"strconv"
)

func GetRandomItems(amount int) ([]xmodel.Item, error) {
	db := xhandler.ConnectDBHandler()
	defer db.Close()

	query := "SELECT * FROM items ORDER BY RAND() LIMIT ?"

	rows, err := db.Query(query, amount)
	if err != nil {
		log.Println(err)
		return nil, err
	}

	var items []xmodel.Item
	var item xmodel.Item
	for rows.Next() {
		if err := rows.Scan(&item.ID, &item.Name, &item.Price, &item.Description); err != nil {
			return nil, err
		}
		items = append(items, item)
	}

	if len(items) < amount {
		return nil, fmt.Errorf("could not fetch random items")
	}

	return items, nil
}

func GetItems(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectDBHandler()
	defer db.Close()
	query := "SELECT * FROM items"

	rows, err := db.Query(query)
	if err != nil {
		log.Println(err)
		xresponse.PrintError(http.StatusNotFound, "No Item Data Inserted To []Item", w)
		return
	}

	var item xmodel.Item
	var items []xmodel.Item
	for rows.Next() {
		if err := rows.Scan(&item.ID, &item.Name, &item.Price, &item.Description); err != nil {
			xresponse.PrintError(http.StatusNotFound, "No Item Data Inserted To []Item", w)
			return
		}
		items = append(items, item)
	}

	var response xmodel.ItemsResponse

	if len(items) > 0 {
		response.Status = http.StatusOK
		response.Message = "Success"
		response.Data = items
		w.Header().Set("Content-Type", "application/json")
		json.NewEncoder(w).Encode(response)
	} else {
		xresponse.PrintError(http.StatusNotFound, "null []Item", w)
		return
	}
}

func AddItem(w http.ResponseWriter, r *http.Request) {

	db := xhandler.ConnectGormDBHandler()

	err := r.ParseForm()
	xresponse.CheckError(err)

	name := r.Form.Get("name")
	price := r.Form.Get("price")
	description := r.Form.Get("description")

	var item xmodel.Item

	item.Name = name
	item.Price, _ = strconv.Atoi(price)
	item.Description = description

	query := db.Select("name", "price", "description").Create(&item)

	if query != nil {
		xresponse.PrintSuccess(http.StatusOK, "Item Inserted", w)
	} else {
		xresponse.PrintError(http.StatusInternalServerError, "Insert Item Failed", w)
	}
}

func DeleteItem(w http.ResponseWriter, r *http.Request) {

	db := xhandler.ConnectGormDBHandler()

	idItem := r.URL.Query().Get("id")

	var item xmodel.Item

	// check if item exists
	err := db.Where("id = ?", idItem).First(&item).Error
	if err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			xresponse.PrintError(http.StatusNotFound, "Item Not Found", w)
		} else {
			xresponse.PrintError(http.StatusInternalServerError, "Server Error", w)
		}
		return
	}

	err = db.Delete(&item, idItem).Error

	if err != nil {
		xresponse.PrintError(http.StatusInternalServerError, "Delete Item Failed", w)
		return
	}

	xresponse.PrintSuccess(http.StatusOK, "Item Deleted", w)
}

func GetItem(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectDBHandler()
	defer db.Close()

	idItem := r.URL.Query().Get("id")

	var item xmodel.Item
	query := "SELECT * FROM items WHERE id = ?"

	err := db.QueryRow(query, idItem).Scan(&item.ID, &item.Name, &item.Price, &item.Description, &item.Image, &item.CategoryID)

	if err != nil {
		log.Println(err)
		xresponse.PrintError(http.StatusNotFound, "Item not found", w)
		return
	}

	var response xmodel.ItemResponse
	response.Status = http.StatusOK
	response.Message = "Success"
	response.Data = item
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}

func UpdateItem(w http.ResponseWriter, r *http.Request) {

	db := xhandler.ConnectGormDBHandler()

	err := r.ParseForm()
	xresponse.CheckError(err)

	idItem := r.Form.Get("id")
	name := r.Form.Get("name")
	price := r.Form.Get("price")
	description := r.Form.Get("description")
	imagex := r.Form.Get("image")
	category_idx := r.Form.Get("category_id")

	var item xmodel.Item
	item.ID, _ = strconv.Atoi(idItem)

	query := db.First(&item, item.ID)
	if query.Error != nil {
		xresponse.PrintError(http.StatusNotFound, "Item Not Found", w)
		return
	}

	if name != "" {
		item.Name = name
	}

	if price != "" {
		priceInt, err := strconv.Atoi(price)
		if err == nil {
			item.Price = priceInt
		}
	}

	if description != "" {
		item.Description = description
	}

	if imagex != "" {
		item.Image = imagex
	}

	if category_idx != "" {
		category_idx_int, err := strconv.Atoi(category_idx)
		if err == nil {
			item.CategoryID = category_idx_int
		}
	}

	query = db.Save(&item)

	if query != nil {
		xresponse.PrintSuccess(http.StatusOK, "Item Updated", w)
	} else {
		xresponse.PrintError(http.StatusInternalServerError, "Update Item Failed", w)
	}
}
