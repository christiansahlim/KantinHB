package com.rpll.kantinhb.data.api

import com.rpll.kantinhb.data.request.LoginRequest
import com.rpll.kantinhb.data.response.AddItemResponse
import com.rpll.kantinhb.data.response.AddtoCartResponse
import com.rpll.kantinhb.data.response.CartResponse
import com.rpll.kantinhb.data.response.CheckoutResponse
import com.rpll.kantinhb.data.response.DeleteCartResponse
import com.rpll.kantinhb.data.response.DeleteItemResponse
import com.rpll.kantinhb.data.response.DeleteTransactionResponse
import com.rpll.kantinhb.data.response.DeleteUserResponse
import com.rpll.kantinhb.data.response.ItemResponse
import com.rpll.kantinhb.data.response.ItemsResponse
import com.rpll.kantinhb.data.response.LoginResponse
import com.rpll.kantinhb.data.response.LogoutResponse
import com.rpll.kantinhb.data.response.RegisterResponse
import com.rpll.kantinhb.data.response.TransactionResponse
import com.rpll.kantinhb.data.response.TransactionsResponse
import com.rpll.kantinhb.data.response.UpdateCartResponse
import com.rpll.kantinhb.data.response.UpdateItemResponse
import com.rpll.kantinhb.data.response.UpdateTransactionResponse
import com.rpll.kantinhb.data.response.UpdateUserResponse
import com.rpll.kantinhb.data.response.UserResponse
import com.rpll.kantinhb.data.response.UsersResponse
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
    suspend fun getUsers(): Response<UsersResponse>

    @GET("/user")
    suspend fun getUser(): Response<UserResponse>

    @PUT("/user")
    @Multipart
    suspend fun updateUser(
        @Part profile_picture: MultipartBody.Part? = null,
        @Part("email") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody
    ): Response<UpdateUserResponse>

    @PUT("/user/edit")
    suspend fun updateUserEdit(): Response<UpdateUserResponse>

    @DELETE("/user")
    suspend fun deleteUser(): Response<DeleteUserResponse>

    // Item Endpoint
    @GET("/items")
    suspend fun getItems(): Response<ItemsResponse>

    @GET("/item")
    suspend fun getItem(): Response<ItemResponse>

    @POST("/item")
    @Multipart
    suspend fun addItem(
        @Part itemDetails: MultipartBody.Part? = null,
        // Additional item details if needed
    ): Response<AddItemResponse>

    @PUT("/item")
    @Multipart
    suspend fun updateItem(
        @Part itemDetails: MultipartBody.Part? = null,
        // Additional item details if needed
    ): Response<UpdateItemResponse>

    @DELETE("/item")
    suspend fun deleteItem(): Response<DeleteItemResponse>

    // Transaction Endpoint
    @GET("/transactions/user")
    suspend fun getTransactionsUser(): Response<TransactionResponse>

    @GET("/transactions")
    suspend fun getTransactions(): Response<TransactionsResponse>

    @GET("/transaction")
    suspend fun getTransaction(): Response<TransactionResponse>

    @PUT("/transaction")
    suspend fun updateTransaction(): Response<UpdateTransactionResponse>

    @DELETE("/transaction")
    suspend fun deleteTransaction(): Response<DeleteTransactionResponse>

    // Cart Endpoint
    @GET("/cart/items")
    suspend fun getItemsFromCart(): Response<CartResponse>

    @GET("/cart/item")
    suspend fun getItemFromCart(): Response<CartResponse>

    @POST("/cart/item")
    suspend fun addItemToCart(): Response<AddtoCartResponse>

    @PUT("/cart/item")
    suspend fun updateCartItem(): Response<UpdateCartResponse>

    @DELETE("/cart/item")
    suspend fun deleteCartItem(): Response<DeleteCartResponse>

    @POST("/cart/checkout")
    suspend fun checkoutCart(): Response<CheckoutResponse>
}
