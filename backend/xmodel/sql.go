package xmodel

import "time"

type Items struct {
	ID          int    `json:"id"`
	Name        string `json:"name"`
	Price       int    `json:"price"`
	Description string `json:"description"`
	Image 		string `json:"image"`
	ID_Category int    `json:"id_category"`
}

type Carts struct {
	ID_User  int `json:"id_user"`
	ID_Item  int `json:"id_item"`
	Quantity int `json:"quantity"`
}

type Transactions struct {
	ID       int       `json:"id"`
	ID_User  int       `json:"user_id"`
	Datetime time.Time `json:"datetime"`
	Status   string    `json:"status"`
	Method   string    `json:"method"`
}

type Detailed_Transactions struct {
	ID_Transaction int `json:"id_transaction"`
	ID_Item        int `json:"id_item"`
	Quantity       int `json:"quantity"`
}
