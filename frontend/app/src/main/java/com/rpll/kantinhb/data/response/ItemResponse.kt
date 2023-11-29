package com.rpll.kantinhb.data.response

import com.google.gson.annotations.SerializedName

data class ItemResponse(

	@field:SerializedName("data")
	val data: ItemData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class ItemData(

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int
)
