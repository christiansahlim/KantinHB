package xmodel

import "time"

type User struct {
	ID       int    `json:"id"`
	Name     string `json:"name"`
	Email    string `json:"email"`
	Password string `json:"-"`
	Admin    bool   `json:"admin"`
}

type Category struct {
	ID    	int    `json:"id"`
	Name  	string `json:"name"`
	Image 	string `json:"image"`
}

type Item struct {
	ID          int    `json:"id"`
	Name        string `json:"name"`
	Price       int    `json:"price"`
	Description string `json:"description"`
	Image 		string `json:"image"`
	CategoryID 	int    `json:"category_id"`
}

type DetailedCart struct {
	Item     Item `json:"item"`
	Quantity int  `json:"quantity"`
}

type Cart struct {
	UserID int            `json:"user_id"`
	Items  []DetailedCart `json:"items"`
}

type DetailedTransaction struct {
	Item     Item `json:"item"`
	Quantity int  `json:"quantity"`
}

type Transaction struct {
	ID       int                   `json:"id"`
	User     User                  `json:"user"`
	Datetime time.Time             `json:"datetime"`
	Status   string                `json:"status"`
	Method   string                `json:"method"`
	Items    []DetailedTransaction `json:"items"`
}
