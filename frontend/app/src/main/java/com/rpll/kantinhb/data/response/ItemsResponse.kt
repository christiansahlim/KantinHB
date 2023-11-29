package com.rpll.kantinhb.data.response

import com.google.gson.annotations.SerializedName

data class ItemsResponse(

	@field:SerializedName("data")
	val data: List<DataItems>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataItems(

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int
)
