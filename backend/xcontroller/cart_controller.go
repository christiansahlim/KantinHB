package xcontroller

import (
	"KantinHB/xhandler"
	"KantinHB/xmodel"
	"KantinHB/xresponse"
	"encoding/json"
	"gorm.io/gorm"
	"log"
	"net/http"
	"strconv"
	"time"
)

func GetItemsFromCart(w http.ResponseWriter, r *http.Request) {

	// Connect to the database
	db := xhandler.ConnectGormDBHandler()

	// Retrieve the user's cart items from the database
	var cartItems []xmodel.Carts
	err := db.Where("id_user = ?", xhandler.GetOnlineUserId(r)).Find(&cartItems).Error
	if err != nil {
		xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve cart items", w)
		return
	}

	if len(cartItems) == 0 {
		xresponse.PrintError(http.StatusNotFound, "Empty cart", w)
		return
	}

	// Get detailed information about each item in the user's cart
	var detailedCart []xmodel.DetailedCart
	for _, cartItem := range cartItems {
		var item2 xmodel.Items
		err = db.First(&item2, cartItem.ID_Item).Error
		var item = xmodel.Item{
			ID:       		item2.ID,
			Name:	 		item2.Name,
			Price: 			item2.Price,
			Description: 	item2.Description,
			Image: 			item2.Image,
			CategoryID: 	item2.ID_Category,
		}
		if err != nil {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve item", w)
			return
		}
		detailedCart = append(detailedCart, xmodel.DetailedCart{
			Item:     item,
			Quantity: cartItem.Quantity,
		})
	}

	// Return the detailed cart information as a JSON xresponse
	resp := xmodel.CartResponse{
		Status:  http.StatusOK,
		Message: "Success",
		Data:    detailedCart,
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(resp)
}

func GetItemFromCart(w http.ResponseWriter, r *http.Request) {
	// Connect to the database
	db := xhandler.ConnectGormDBHandler()

	// Get the item ID from the query parameters
	itemIDStr := r.URL.Query().Get("id")
	itemID, err := strconv.Atoi(itemIDStr)
	if err != nil {
		xresponse.PrintError(http.StatusBadRequest, "Invalid item ID", w)
		return
	}

	// Retrieve the user's cart items from the database
	var cartItem xmodel.Carts
	err = db.Where("id_user = ? AND id_item = ?", xhandler.GetOnlineUserId(r), itemID).First(&cartItem).Error
	if err != nil {
		if err == gorm.ErrRecordNotFound {
			xresponse.PrintError(http.StatusNotFound, "Item not found in cart", w)
		} else {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve cart item", w)
		}
		return
	}

	// Get detailed information about the item in the user's cart
	var item2 xmodel.Items
	err = db.First(&item2, itemID).Error
	var item = xmodel.Item{
		ID:       		item2.ID,
		Name:	 		item2.Name,
		Price: 			item2.Price,
		Description: 	item2.Description,
		Image: 			item2.Image,
		CategoryID: 	item2.ID_Category,
	}
	if err != nil {
		xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve item", w)
		return
	}
	detailedCart := xmodel.DetailedCart{
		Item:     item,
		Quantity: cartItem.Quantity,
	}

	// Return the detailed cart information as a JSON xresponse
	resp := xmodel.CartResponse{
		Status:  http.StatusOK,
		Message: "Success",
		Data:    []xmodel.DetailedCart{detailedCart},
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(resp)
}

func AddItemToCart(w http.ResponseWriter, r *http.Request) {
	// Connect to the database
	db := xhandler.ConnectGormDBHandler()

	// Parse the request body to get the item ID and quantity to add to the cart
	err := r.ParseForm()
	xresponse.CheckError(err)

	id, errx := strconv.Atoi(r.Form.Get("id"))
	quantity, erry := strconv.Atoi(r.Form.Get("quantity"))

	xresponse.CheckError(errx)
	xresponse.CheckError(erry)

	// Check if the item ID is valid
	var item2 xmodel.Items
	err = db.First(&item2, id).Error
	if err != nil {
		if err == gorm.ErrRecordNotFound {
			xresponse.PrintError(http.StatusNotFound, "Item not found", w)
		} else {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve item", w)
		}
		return
	}

	// Check if the item is already in the user's cart
	var cartItem xmodel.Carts
	err = db.Where("id_user = ? AND id_item = ?", xhandler.GetOnlineUserId(r), id).First(&cartItem).Error
	if err != nil {
		if err != gorm.ErrRecordNotFound {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to check cart", w)
			return
		} else {
			cartItem = xmodel.Carts{
				ID_User:  xhandler.GetOnlineUserId(r),
				ID_Item:  id,
				Quantity: quantity,
			}
			err = db.Create(&cartItem).Error
			if err != nil {
				xresponse.PrintError(http.StatusInternalServerError, "Failed to add item to cart", w)
				return
			}
		}
	} else {
		xresponse.PrintError(http.StatusInternalServerError, "Item already in cart", w)
		return
	}

	// Return a success xresponse
	xresponse.PrintSuccess(http.StatusOK, "Item added to cart", w)
}

func UpdateCartItem(w http.ResponseWriter, r *http.Request) {
	// Connect to the database
	db := xhandler.ConnectGormDBHandler()

	// Parse the request body to get the item ID and quantity to update in the cart
	err := r.ParseForm()
	xresponse.CheckError(err)

	id, errx := strconv.Atoi(r.Form.Get("id"))
	quantity, erry := strconv.Atoi(r.Form.Get("quantity"))

	xresponse.CheckError(errx)
	xresponse.CheckError(erry)

	// Check if the item ID is valid
	var item2 xmodel.Items
	err = db.First(&item2, id).Error
	if err != nil {
		if err == gorm.ErrRecordNotFound {
			xresponse.PrintError(http.StatusNotFound, "Item not found", w)
		} else {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve item", w)
		}
		return
	}

	// Check if the item is already in the user's cart
	var cartItem xmodel.Carts
	err = db.Where("id_user = ? AND id_item = ?", xhandler.GetOnlineUserId(r), id).First(&cartItem).Error
	if err != nil {
		if err != gorm.ErrRecordNotFound {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to check cart", w)
			return
		} else {
			xresponse.PrintError(http.StatusNotFound, "Item not in cart", w)
			return
		}
	} else {
		cartItem.Quantity = quantity
		err = db.Where("id_user = ? AND id_item = ?", xhandler.GetOnlineUserId(r), id).Save(&cartItem).Error
		if err != nil {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to update cart item quantity", w)
			return
		}
	}

	// Return a success xresponse
	xresponse.PrintSuccess(http.StatusOK, "Item updated in cart successfully", w)
}

func DeleteCartItem(w http.ResponseWriter, r *http.Request) {
	// Connect to the database
	db := xhandler.ConnectGormDBHandler()

	// Parse the request body to get the item ID to delete from the cart
	id := r.URL.Query().Get("id")

	// Check if the item ID is valid
	var item xmodel.Item
	err := db.First(&item, id).Error
	if err != nil {
		if err == gorm.ErrRecordNotFound {
			xresponse.PrintError(http.StatusNotFound, "Item not found", w)
		} else {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve item", w)
		}
		return
	}

	// Check if the item is already in the user's cart
	var cartItem xmodel.Carts
	err = db.Where("id_user = ? AND id_item = ?", xhandler.GetOnlineUserId(r), id).First(&cartItem).Error
	if err != nil {
		if err != gorm.ErrRecordNotFound {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to check cart", w)
			return
		} else {
			xresponse.PrintError(http.StatusNotFound, "Item not in cart", w)
			return
		}
	} else {
		err = db.Where("id_user = ? AND id_item = ?", xhandler.GetOnlineUserId(r), id).Delete(&cartItem).Error
		if err != nil {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to delete cart item", w)
			return
		}
	}

	// Return a success xresponse
	xresponse.PrintSuccess(http.StatusOK, "Item removed from cart successfully", w)
}

func CheckoutCart(w http.ResponseWriter, r *http.Request) {
	// Connect to the database
	db := xhandler.ConnectGormDBHandler()

	errx := r.ParseForm()
	xresponse.CheckError(errx)

	method := r.Form.Get("method")
	status := r.Form.Get("status")

	// Get the current user's cart
	var cart []xmodel.Carts
	err := db.Where("id_user = ?", xhandler.GetOnlineUserId(r)).Find(&cart).Error
	if err != nil {
		if err != gorm.ErrRecordNotFound {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to get cart", w)
			return
		} else {
			xresponse.PrintSuccess(http.StatusOK, "Cart is empty", w)
			return
		}
	}

	if len(cart) == 0 {
		xresponse.PrintError(http.StatusNotFound, "Empty cart", w)
		return
	}

	// Calculate the total cost of the transaction
	totalCost := 0
	for _, item := range cart {
		var item2 xmodel.Items
		err = db.First(&item2, item.ID_Item).Error
		var dbItem = xmodel.Item{
			ID:       		item2.ID,
			Name:	 		item2.Name,
			Price: 			item2.Price,
			Description: 	item2.Description,
			Image: 			item2.Image,
			CategoryID: 	item2.ID_Category,
		}
		err = db.First(&dbItem, item.ID_Item).Error
		if err != nil {
			if err == gorm.ErrRecordNotFound {
				xresponse.PrintError(http.StatusNotFound, "Item not found", w)
			} else {
				xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve item", w)
			}
			return
		}
		totalCost += dbItem.Price * item.Quantity
	}

	dbx := xhandler.ConnectDBHandler()
	defer dbx.Close()

	var user xmodel.User
	query := "SELECT * FROM users WHERE id = ?"

	errxx := dbx.QueryRow(query, xhandler.GetOnlineUserId(r)).Scan(&user.ID, &user.Name, &user.Email, &user.Password, &user.Admin)

	if errxx != nil {
		log.Println(errxx)
		xresponse.PrintError(http.StatusNotFound, "User not found", w)
		return
	}

	// Create a new transaction

	transactionx := xmodel.Transactions{
		ID_User:  xhandler.GetOnlineUserId(r),
		Datetime: time.Now(),
		Status:   status,
		Method:   method,
	}

	// Add the transaction to the transactions table
	err = db.Create(&transactionx).Error
	if err != nil {
		xresponse.PrintError(http.StatusInternalServerError, "Failed to create transaction", w)
		return
	}

	transaction := xmodel.Transaction{
		ID:       transactionx.ID,
		User:     user,
		Datetime: time.Now(),
		Status:   status,
		Method:   method,
	}

	// Add the items in the cart to the transaction
	for _, item := range cart {
		var item2 xmodel.Items
		err = db.First(&item2, item.ID_Item).Error
		var dbItem = xmodel.Item{
			ID:       		item2.ID,
			Name:	 		item2.Name,
			Price: 			item2.Price,
			Description: 	item2.Description,
			Image: 			item2.Image,
			CategoryID: 	item2.ID_Category,
		}
		if err != nil {
			if err == gorm.ErrRecordNotFound {
				xresponse.PrintError(http.StatusNotFound, "Item not found", w)
			} else {
				xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve item", w)
			}
			return
		}

		detailedTransaction := xmodel.DetailedTransaction{
			Item:     dbItem,
			Quantity: item.Quantity,
		}

		transaction.Items = append(transaction.Items, detailedTransaction)

		// Add the item to the detailed_transactions table

		detailedTransactionRecord := xmodel.Detailed_Transactions{
			ID_Item:        dbItem.ID,
			ID_Transaction: transactionx.ID,
			Quantity:       item.Quantity,
		}
		err = db.Create(&detailedTransactionRecord).Error
		if err != nil {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to add item to transaction", w)
			return
		}
	}

	// Clear the user's cart
	err = db.Where("id_user = ?", xhandler.GetOnlineUserId(r)).Delete(&xmodel.Carts{}).Error
	if err != nil {
		xresponse.PrintError(http.StatusInternalServerError, "Failed to clear cart", w)
		return
	}

	response := xmodel.TransactionResponse{
		Status:  http.StatusOK,
		Message: "Transaction completed successfully, total is " + strconv.Itoa(totalCost),
		Data:    transaction,
	}

	go SendCheckout(transaction, totalCost)

	// Return a success xresponse
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}
