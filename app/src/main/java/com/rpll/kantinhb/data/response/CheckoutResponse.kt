package com.rpll.kantinhb.data.response

import com.google.gson.annotations.SerializedName

data class CheckoutResponse(

	@field:SerializedName("data")
	val data: CheckoutData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class CheckoutData(

	@field:SerializedName("datetime")
	val datetime: String,

	@field:SerializedName("method")
	val method: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("user")
	val user: UserData,

	@field:SerializedName("items")
	val items: List<ItemCheckout>,

	@field:SerializedName("status")
	val status: String
)

data class ItemCheckout(

	@field:SerializedName("item")
	val item: CheckoutItem,

	@field:SerializedName("quantity")
	val quantity: Int
)

data class UserData(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("admin")
	val admin: Boolean,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)

data class CheckoutItem(

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int
)
