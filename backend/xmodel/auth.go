package xmodel

type UserAuth struct {
	Email    string `json:"email"`
	Password string `json:"password"`
	Admin    bool   `json:"admin"`
}
