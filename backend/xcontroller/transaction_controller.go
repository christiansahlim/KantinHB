package xcontroller

import (
	"KantinHB/xhandler"
	"KantinHB/xmodel"
	"KantinHB/xresponse"
	"encoding/json"
	"errors"
	"gorm.io/gorm"
	"net/http"
	"strconv"
)

func GetTransactionsUser(w http.ResponseWriter, r *http.Request) {
	// Connect to the database
	db := xhandler.ConnectGormDBHandler()

	// Get all transactions from the transactions table
	var transactions []xmodel.Transactions
	var transactionsv []xmodel.Transaction

	err := db.Where("id_user = ?", xhandler.GetOnlineUserId(r)).Find(&transactions).Error
	if err != nil {
		xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve transactions", w)
		return
	}

	// Iterate through the transactions to add the user and detailed items information
	for _, transaction := range transactions {
		// Get the user information for the transaction
		var user xmodel.User
		err = db.First(&user, transaction.ID_User).Error
		if err != nil {
			if err == gorm.ErrRecordNotFound {
				xresponse.PrintError(http.StatusNotFound, "User not found", w)
			} else {
				xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve user", w)
			}
			return
		}

		// Get the detailed items information for the transaction
		var detailedTransactions []xmodel.Detailed_Transactions
		err = db.Where("id_transaction = ?", transaction.ID).Find(&detailedTransactions).Error
		if err != nil {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve detailed transactions", w)
			return
		}

		transactionxm := xmodel.Transaction{
			ID:       transaction.ID,
			User:     user,
			Datetime: transaction.Datetime,
			Status:   transaction.Status,
			Method:   transaction.Method,
			Items:    []xmodel.DetailedTransaction{},
		}

		for _, detailedTransaction := range detailedTransactions {
			var item xmodel.Item
			err = db.First(&item, detailedTransaction.ID_Item).Error
			if err != nil {
				if err == gorm.ErrRecordNotFound {
					xresponse.PrintError(http.StatusNotFound, "Item not found", w)
				} else {
					xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve item", w)
				}
				return
			}

			detailedItem := xmodel.DetailedTransaction{
				Item:     item,
				Quantity: detailedTransaction.Quantity,
			}
			transactionxm.Items = append(transactionxm.Items, detailedItem)
		}
		transactionsv = append(transactionsv, transactionxm)
	}

	response := xmodel.TransactionsResponse{
		Status:  http.StatusOK,
		Message: "Transactions retrieved successfully",
		Data:    transactionsv,
	}

	// Return a success xresponse
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}

func GetTransactions(w http.ResponseWriter, r *http.Request) {
	// Connect to the database
	db := xhandler.ConnectGormDBHandler()

	// Get all transactions from the transactions table
	var transactions []xmodel.Transactions
	var transactionsv []xmodel.Transaction

	err := db.Find(&transactions).Error
	if err != nil {
		xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve transactions", w)
		return
	}

	// Iterate through the transactions to add the user and detailed items information
	for _, transaction := range transactions {
		// Get the user information for the transaction
		var user xmodel.User
		err = db.First(&user, transaction.ID_User).Error
		if err != nil {
			if err == gorm.ErrRecordNotFound {
				xresponse.PrintError(http.StatusNotFound, "User not found", w)
			} else {
				xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve user", w)
			}
			return
		}

		// Get the detailed items information for the transaction
		var detailedTransactions []xmodel.Detailed_Transactions
		err = db.Where("id_transaction = ?", transaction.ID).Find(&detailedTransactions).Error
		if err != nil {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve detailed transactions", w)
			return
		}

		transactionxm := xmodel.Transaction{
			ID:       transaction.ID,
			User:     user,
			Datetime: transaction.Datetime,
			Status:   transaction.Status,
			Method:   transaction.Method,
			Items:    []xmodel.DetailedTransaction{},
		}

		for _, detailedTransaction := range detailedTransactions {
			var item xmodel.Item
			err = db.First(&item, detailedTransaction.ID_Item).Error
			if err != nil {
				if err == gorm.ErrRecordNotFound {
					xresponse.PrintError(http.StatusNotFound, "Item not found", w)
				} else {
					xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve item", w)
				}
				return
			}

			detailedItem := xmodel.DetailedTransaction{
				Item:     item,
				Quantity: detailedTransaction.Quantity,
			}
			transactionxm.Items = append(transactionxm.Items, detailedItem)
		}
		transactionsv = append(transactionsv, transactionxm)
	}

	response := xmodel.TransactionsResponse{
		Status:  http.StatusOK,
		Message: "Transactions retrieved successfully",
		Data:    transactionsv,
	}

	// Return a success xresponse
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}

func GetTransaction(w http.ResponseWriter, r *http.Request) {
	// Connect to the database
	db := xhandler.ConnectGormDBHandler()

	// Get all transactions from the transactions table
	var transaction xmodel.Transactions

	id := r.URL.Query().Get("id")

	err := db.Where("id = ?", id).First(&transaction).Error
	if err != nil {
		xresponse.PrintError(http.StatusNotFound, "Transaction not found", w)
		return
	}

	// Iterate through the transactions to add the user and detailed items information

	// Get the user information for the transaction
	var user xmodel.User
	err = db.First(&user, transaction.ID_User).Error
	if err != nil {
		if err == gorm.ErrRecordNotFound {
			xresponse.PrintError(http.StatusNotFound, "User not found", w)
		} else {
			xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve user", w)
		}
		return
	}

	// Get the detailed items information for the transaction
	var detailedTransactions []xmodel.Detailed_Transactions
	err = db.Where("id_transaction = ?", transaction.ID).Find(&detailedTransactions).Error
	if err != nil {
		xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve detailed transactions", w)
		return
	}

	transactionxm := xmodel.Transaction{
		ID:       transaction.ID,
		User:     user,
		Datetime: transaction.Datetime,
		Status:   transaction.Status,
		Method:   transaction.Method,
		Items:    []xmodel.DetailedTransaction{},
	}

	for _, detailedTransaction := range detailedTransactions {
		var item xmodel.Item
		err = db.First(&item, detailedTransaction.ID_Item).Error
		if err != nil {
			if err == gorm.ErrRecordNotFound {
				xresponse.PrintError(http.StatusNotFound, "Item not found", w)
			} else {
				xresponse.PrintError(http.StatusInternalServerError, "Failed to retrieve item", w)
			}
			return
		}

		detailedItem := xmodel.DetailedTransaction{
			Item:     item,
			Quantity: detailedTransaction.Quantity,
		}
		transactionxm.Items = append(transactionxm.Items, detailedItem)
	}

	response := xmodel.TransactionResponse{
		Status:  http.StatusOK,
		Message: "Transactions retrieved successfully",
		Data:    transactionxm,
	}

	// Return a success xresponse
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}

func UpdateTransaction(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectGormDBHandler()

	err := r.ParseForm()
	xresponse.CheckError(err)

	idTransaction := r.Form.Get("id")
	status := r.Form.Get("status")
	method := r.Form.Get("method")

	var transaction xmodel.Transactions
	transaction.ID, _ = strconv.Atoi(idTransaction)

	query := db.First(&transaction, transaction.ID)
	if query.Error != nil {
		xresponse.PrintError(http.StatusNotFound, "Transaction Not Found", w)
		return
	}

	if status != "" {
		transaction.Status = status
	}

	if method != "" {
		transaction.Method = method
	}

	query = db.Save(&transaction)

	if query != nil {
		xresponse.PrintSuccess(http.StatusOK, "Transaction Updated", w)
	} else {
		xresponse.PrintError(http.StatusInternalServerError, "Update Transaction Failed", w)
	}
}

func DeleteTransaction(w http.ResponseWriter, r *http.Request) {
	db := xhandler.ConnectGormDBHandler()

	idTransaction := r.URL.Query().Get("id")

	var transaction xmodel.Transactions

	// check if transaction exists
	err := db.Where("id = ?", idTransaction).First(&transaction).Error
	if err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			xresponse.PrintError(http.StatusNotFound, "Transaction Not Found", w)
		} else {
			xresponse.PrintError(http.StatusInternalServerError, "Server Error", w)
		}
		return
	}

	err = db.Where("id_transaction = ?", idTransaction).Delete(&xmodel.Detailed_Transactions{}).Error

	if err != nil {
		xresponse.PrintError(http.StatusInternalServerError, "Delete Detailed TransactionsFailed", w)
		return
	}

	err = db.Delete(&transaction, idTransaction).Error

	if err != nil {
		xresponse.PrintError(http.StatusInternalServerError, "Delete Transaction Failed", w)
		return
	}

	xresponse.PrintSuccess(http.StatusOK, "Transaction Deleted", w)
}
