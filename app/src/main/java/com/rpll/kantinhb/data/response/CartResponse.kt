package com.rpll.kantinhb.data.response

import com.google.gson.annotations.SerializedName

data class CartResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
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

data class DataItem(

	@field:SerializedName("item")
	val item: Item,

	@field:SerializedName("quantity")
	val quantity: Int
)
