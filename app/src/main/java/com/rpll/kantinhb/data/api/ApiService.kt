package com.rpll.kantinhb.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("/register")
    @Multipart
    suspend fun registerUser(
        @Part profile_picture: MultipartBody.Part? = null,
        @Part("email") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody
    ): Response<RegisterResponse>

    @POST("/login")
    fun loginUser(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("/logout")
    fun logout(): Call<LogoutResponse>

    // User Endpoint
    @GET("/users")
    suspend fun getUsers(): Response<UserResponse>

    @GET("/user")
    suspend fun getUser(): Response<UserResponse>

    @POST("/user")
    @Multipart
    suspend fun addUser(
        @Part profile_picture: MultipartBody.Part? = null,
        @Part("email") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody
    ): Response<UserResponse>

    @PUT("/user")
    @Multipart
    suspend fun updateUser(
        @Part profile_picture: MultipartBody.Part? = null,
        @Part("email") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody
    ): Response<UserResponse>

    @PUT("/user/edit")
    suspend fun updateUserEdit(): Response<UserResponse>

    @DELETE("/user")
    suspend fun deleteUser(): Response<UserResponse>

    // Item Endpoint
    @GET("/items")
    suspend fun getItems(): Response<ItemResponse>

    @GET("/item")
    suspend fun getItem(): Response<ItemResponse>

    @POST("/item")
    @Multipart
    suspend fun addItem(
        @Part itemDetails: MultipartBody.Part? = null,
        // Additional item details if needed
    ): Response<ItemResponse>

    @PUT("/item")
    @Multipart
    suspend fun updateItem(
        @Part itemDetails: MultipartBody.Part? = null,
        // Additional item details if needed
    ): Response<ItemResponse>

    @DELETE("/item")
    suspend fun deleteItem(): Response<ItemResponse>

    // Transaction Endpoint
    @GET("/transactions/user")
    suspend fun getTransactionsUser(): Response<TransactionResponse>

    @GET("/transactions")
    suspend fun getTransactions(): Response<TransactionResponse>

    @GET("/transaction")
    suspend fun getTransaction(): Response<TransactionResponse>

    @PUT("/transaction")
    suspend fun updateTransaction(): Response<TransactionResponse>

    @DELETE("/transaction")
    suspend fun deleteTransaction(): Response<TransactionResponse>

    // Cart Endpoint
    @GET("/cart/items")
    suspend fun getItemsFromCart(): Response<CartItemsResponse>

    @GET("/cart/item")
    suspend fun getItemFromCart(): Response<CartItemResponse>

    @POST("/cart/item")
    suspend fun addItemToCart(): Response<CartItemResponse>

    @PUT("/cart/item")
    suspend fun updateCartItem(): Response<CartItemResponse>

    @DELETE("/cart/item")
    suspend fun deleteCartItem(): Response<CartItemResponse>

    @POST("/cart/checkout")
    suspend fun checkoutCart(): Response<CheckoutResponse>
}
