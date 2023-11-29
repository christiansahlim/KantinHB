package xmodel

type SuccessResponse struct {
	Status  int    `json:"status"`
	Message string `json:"message"`
}

type ErrorResponse struct {
	Status  int    `json:"status"`
	Message string `json:"message"`
}

type UserResponse struct {
	Status  int    `json:"status"`
	Message string `json:"message"`
	Data    User   `json:"data"`
}

type UsersResponse struct {
	Status  int    `json:"status"`
	Message string `json:"message"`
	Data    []User `json:"data"`
}

type ItemResponse struct {
	Status  int    `json:"status"`
	Message string `json:"message"`
	Data    Item   `json:"data"`
}

type ItemsResponse struct {
	Status  int    `json:"status"`
	Message string `json:"message"`
	Data    []Item `json:"data"`
}

type CategoriesResponse struct {
	Status  int        `json:"status"`
	Message string     `json:"message"`
	Data    []Category `json:"data"`
}

type CategoryResponse struct {
	Status  int      `json:"status"`
	Message string   `json:"message"`
	Data    Category `json:"data"`
}

type CartResponse struct {
	Status  int            `json:"status"`
	Message string         `json:"message"`
	Data    []DetailedCart `json:"data"`
}

type TransactionResponse struct {
	Status  int         `json:"status"`
	Message string      `json:"message"`
	Data    Transaction `json:"data"`
}

type TransactionsResponse struct {
	Status  int           `json:"status"`
	Message string        `json:"message"`
	Data    []Transaction `json:"data"`
}
