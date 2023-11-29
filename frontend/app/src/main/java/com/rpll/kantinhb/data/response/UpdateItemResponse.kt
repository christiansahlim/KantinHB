package com.rpll.kantinhb.data.response

import com.google.gson.annotations.SerializedName

data class UpdateItemResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)
