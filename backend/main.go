package main

import (
	c "KantinHB/xcontroller"
	"KantinHB/xhandler"
	"KantinHB/xresponse"
	_ "github.com/go-sql-driver/mysql" // Connection
	"github.com/gorilla/mux"           // Router
	"github.com/joho/godotenv"
	"github.com/rs/cors"
	"log"
	"net/http"
	"os"
)

func main() {
	// Load ENV
	err := godotenv.Load()
	xresponse.CheckError(err)

	// Clear Redis Cache
	err = c.ClearRedisCache(c.GetRedisClient())
	xresponse.CheckError(err)

	// Start Mux
	router := mux.NewRouter()

	// General Endpoint
	router.HandleFunc("/register", c.UserRegister).Methods("POST")
	router.HandleFunc("/login", c.UserLogin).Methods("POST")
	router.HandleFunc("/logout", c.Logout).Methods("POST")

	// User Endpoint
	router.HandleFunc("/users", xhandler.Authenticate(c.GetUsers, true)).Methods("GET")
	router.HandleFunc("/user", c.GetUser).Methods("GET")
	router.HandleFunc("/user", xhandler.Authenticate(c.AddUser, true)).Methods("POST")
	router.HandleFunc("/user", xhandler.Authenticate(c.UpdateUser, true)).Methods("PUT")
	router.HandleFunc("/user/edit", c.UpdateUserEdit).Methods("PUT")
	router.HandleFunc("/user", xhandler.Authenticate(c.DeleteUser, true)).Methods("DELETE")

	// Item Endpoint
	router.HandleFunc("/items", c.GetItems).Methods("GET")
	router.HandleFunc("/item", c.GetItem).Methods("GET")
	router.HandleFunc("/item", c.AddItem).Methods("POST")
	router.HandleFunc("/item", c.UpdateItem).Methods("PUT")
	router.HandleFunc("/item", c.DeleteItem).Methods("DELETE")

	// Transaction Endpoint
	router.HandleFunc("/transactions/user", xhandler.Authenticate(c.GetTransactionsUser, false)).Methods("GET")
	router.HandleFunc("/transactions", xhandler.Authenticate(c.GetTransactions, true)).Methods("GET")
	router.HandleFunc("/transaction", xhandler.Authenticate(c.GetTransaction, true)).Methods("GET")
	router.HandleFunc("/transaction", xhandler.Authenticate(c.UpdateTransaction, true)).Methods("PUT")
	router.HandleFunc("/transaction", xhandler.Authenticate(c.DeleteTransaction, true)).Methods("DELETE")

	// Cart Endpoint
	router.HandleFunc("/cart/items", xhandler.Authenticate(c.GetItemsFromCart, false)).Methods("GET")
	router.HandleFunc("/cart/item", xhandler.Authenticate(c.GetItemFromCart, false)).Methods("GET")
	router.HandleFunc("/cart/item", xhandler.Authenticate(c.AddItemToCart, false)).Methods("POST")
	router.HandleFunc("/cart/item", xhandler.Authenticate(c.UpdateCartItem, false)).Methods("PUT")
	router.HandleFunc("/cart/item", xhandler.Authenticate(c.DeleteCartItem, false)).Methods("DELETE")
	router.HandleFunc("/cart/checkout", xhandler.Authenticate(c.CheckoutCart, false)).Methods("POST")

	// Category Endpoint
	router.HandleFunc("/categories", c.GetCategories).Methods("GET")
	router.HandleFunc("/category", c.GetCategory).Methods("GET")
	router.HandleFunc("/category", c.AddCategory).Methods("POST")
	router.HandleFunc("/category", c.UpdateCategory).Methods("PUT")
	router.HandleFunc("/category", c.DeleteCategory).Methods("DELETE")

	// CORS
	corsHandler := cors.New(cors.Options{
		AllowedOrigins:   []string{"*"},
		AllowedMethods:   []string{"GET", "POST", "PUT", "DELETE"},
		AllowCredentials: true,
	})

	// Start Server
	http.Handle("/", router)

	log.Println("Starting " + os.Getenv("APP_NAME"))
	log.Println("Connected to port 8081")
	log.Fatal(http.ListenAndServe(":8081", corsHandler.Handler(router)))
}
