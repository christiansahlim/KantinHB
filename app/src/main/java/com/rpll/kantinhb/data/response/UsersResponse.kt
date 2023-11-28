package com.rpll.kantinhb.data.response

import com.google.gson.annotations.SerializedName

data class UsersResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("admin")
	val admin: Boolean,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)
