package com.rpll.kantinhb.data.response

import com.google.gson.annotations.SerializedName

data class TransactionResponse(

	@field:SerializedName("data")
	val data: TransactionData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class TransactionData(

	@field:SerializedName("datetime")
	val datetime: String,

	@field:SerializedName("method")
	val method: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("user")
	val user: UserTransaction,

	@field:SerializedName("items")
	val items: List<ItemsTransaction>,

	@field:SerializedName("status")
	val status: String
)

data class UserTransaction(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("admin")
	val admin: Boolean,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)

data class ItemTransaction(

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int
)

data class ItemsTransaction(

	@field:SerializedName("item")
	val item: ItemTransaction,

	@field:SerializedName("quantity")
	val quantity: Int
)
