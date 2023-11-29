package com.rpll.kantinhb.data.response

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(

	@field:SerializedName("data")
	val data: List<DataCategories>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataCategories(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("image")
	val image: String
)
