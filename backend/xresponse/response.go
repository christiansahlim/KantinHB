package xresponse

import (
	"KantinHB/xmodel"
	"encoding/json"
	"log"
	"net/http"
)

// CheckError General error checking
func CheckError(err error) {
	if err != nil {
		log.Fatal(err)
		// fmt.Println(err)
	}
}

// PrintSuccess Postman output
func PrintSuccess(status int, message string, w http.ResponseWriter) {
	var succResponse xmodel.SuccessResponse
	succResponse.Status = status
	succResponse.Message = message
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(succResponse)
}

func PrintError(status int, message string, w http.ResponseWriter) {
	var errResponse xmodel.ErrorResponse
	errResponse.Status = status
	errResponse.Message = message
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(errResponse)
}
