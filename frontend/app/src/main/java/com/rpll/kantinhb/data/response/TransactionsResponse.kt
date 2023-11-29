package com.rpll.kantinhb.data.response

import com.google.gson.annotations.SerializedName

data class TransactionsResponse(

	@field:SerializedName("data")
	val data: List<TransactionItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class User(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("admin")
	val admin: Boolean,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)

data class ItemsItem(

	@field:SerializedName("item")
	val item: Item,

	@field:SerializedName("quantity")
	val quantity: Int
)

data class TransactionItem(

	@field:SerializedName("datetime")
	val datetime: String,

	@field:SerializedName("method")
	val method: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("items")
	val items: List<ItemsItem>,

	@field:SerializedName("status")
	val status: String
)

data class Item(

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int
)
