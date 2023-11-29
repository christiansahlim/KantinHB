package com.rpll.kantinhb.data.api

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
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    @FormUrlEncoded
    suspend fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String
    ): Response<RegisterResponse>

    @POST("/login")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<LoginResponse>

    @POST("/logout")
    fun logout(): Call<LogoutResponse>

    // User Endpoint
    @GET("/users")
    suspend fun getUsers(
        @Header("Cookie") token: String
    ): Response<UsersResponse>

    @GET("/user")
    suspend fun getUser(
        @Header("Cookie") token: String,
        @Query("id") id: Int
    ): Response<UserResponse>

    @PUT("/user")
    @Multipart
    suspend fun updateUser(
        @Header("Cookie") token: String,
        @Part("id") id: RequestBody,
        @Part("email") email: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("name") name: RequestBody?
    ): Response<UpdateUserResponse>

    @DELETE("/user")
    suspend fun deleteUser(
        @Header("Cookie") token: String,
        @Part("id") id: RequestBody
    ): Response<DeleteUserResponse>

    // Item Endpoint
    @GET("/items")
    suspend fun getItems(
        @Header("Cookie") token: String
    ): Response<ItemsResponse>

    @GET("/item")
    suspend fun getItem(
        @Header("Cookie") token: String,
        @Query("id") id: Int
    ): Response<ItemResponse>

    @POST("/item")
    @Multipart
    suspend fun addItem(
        @Header("Cookie") token: String,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody
    ): Response<AddItemResponse>

    @PUT("/item")
    @Multipart
    suspend fun updateItem(
        @Header("Cookie") token: String,
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("price") price: RequestBody?
    ): Response<UpdateItemResponse>

    @DELETE("/item")
    suspend fun deleteItem(
        @Header("Cookie") token: String,
        @Part("id") id: RequestBody
    ): Response<DeleteItemResponse>

    // Transaction Endpoint
    @GET("/transactions/user")
    suspend fun getTransactionsUser(
    
    ): Response<TransactionResponse>

    @GET("/transactions")
    suspend fun getTransactions(
        @Header("Cookie") token: String
    ): Response<TransactionsResponse>

    @GET("/transaction")
    suspend fun getTransaction(
        @Header("Cookie") token: String,
        @Query("id") id: Int
    ): Response<TransactionResponse>

    @PUT("/transaction")
    suspend fun updateTransaction(
        @Header("Cookie") token: String,
        @Part("id") id: RequestBody,
        @Part("status") status: RequestBody?,
        @Part("method") method: RequestBody?
    ): Response<UpdateTransactionResponse>

    @DELETE("/transaction")
    suspend fun deleteTransaction(
        @Header("Cookie") token: String,
        @Part("id") id: RequestBody
    ): Response<DeleteTransactionResponse>

    // Cart Endpoint
    @GET("/cart/items")
    suspend fun getItemsFromCart(
        @Header("Cookie") token: String
    ): Response<CartResponse>

    @GET("/cart/item")
    suspend fun getItemFromCart(
        @Header("Cookie") token: String,
        @Query("id") id: Int
    ): Response<CartResponse>

    @POST("/cart/item")
    suspend fun addItemToCart(
        @Header("Cookie") token: String,
        @Part("id") id: RequestBody,
        @Part("quantity") quantity: RequestBody
    ): Response<AddtoCartResponse>

    @PUT("/cart/item")
    suspend fun updateCartItem(
        @Header("Cookie") token: String,
        @Part("id") id: RequestBody,
        @Part("quantity") quantity: RequestBody
    ): Response<UpdateCartResponse>

    @DELETE("/cart/item")
    suspend fun deleteCartItem(
        @Header("Cookie") token: String,
        @Part("id") id: RequestBody
    ): Response<DeleteCartResponse>

    @POST("/cart/checkout")
    suspend fun checkoutCart(
        @Header("Cookie") token: String
    ): Response<CheckoutResponse>
}
