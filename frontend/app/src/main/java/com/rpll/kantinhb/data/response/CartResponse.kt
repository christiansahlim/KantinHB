package com.rpll.kantinhb.data.response

import com.google.gson.annotations.SerializedName

data class CartResponse(

	@field:SerializedName("data")
	val data: List<CartItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class CartItem(

	@field:SerializedName("item")
	val item: ItemCart,

	@field:SerializedName("quantity")
	val quantity: Int
)

data class ItemCart(

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int
)
