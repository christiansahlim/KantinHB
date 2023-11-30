package com.rpll.kantinhb.data.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.rpll.kantinhb.data.api.ApiConfig
import com.rpll.kantinhb.data.response.CheckoutResponse
import com.rpll.kantinhb.data.response.LoginResponse
import com.rpll.kantinhb.data.response.RegisterResponse
import com.rpll.kantinhb.data.source.DataSource
import com.rpll.kantinhb.model.Category
import com.rpll.kantinhb.model.OrderItem
import com.rpll.kantinhb.model.ProductItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import kotlinx.coroutines.runBlocking

class KantinHBRepository private constructor() {
    private val specialSelectionById: List<Long> = arrayListOf(
        4, 6, 8, 12, 16, 18, 21, 23, 25, 29, 30
    )

    private var _isLoggedIn by mutableStateOf(false)
    val isLoggedIn: Boolean get() = _isLoggedIn

    private var _errorText by mutableStateOf<String?>(null)
    val errorText: String? get() = _errorText

    private val promotions = mutableListOf<Int>()
    private val categories = mutableListOf<Category>()
    private val specialSelection = mutableStateListOf<ProductItem>()
    private val productsByCategoryId = mutableStateListOf<ProductItem>()
    private val orders = mutableListOf<OrderItem>()
    private var myFavorites = mutableListOf<Long>()
    private val isQuickLogin = mutableStateOf(false)
    private val productsFavorites = mutableListOf<ProductItem>()

    private var token: String = "";

    init {
        if (promotions.isEmpty()) {
            DataSource.promotions().forEach {
                promotions.add(it)
            }
        }

        if (categories.isEmpty()) {
            DataSource.categories().forEach {
                categories.add(it)
            }
        }

        if (specialSelection.isEmpty()) {
            DataSource.products().forEach {
                if (it.id in specialSelectionById) {
                    specialSelection.add(it)
                }
            }
        }
    }

    fun getIsQuickLogin(): Flow<MutableState<Boolean>> {
        return flowOf(isQuickLogin)
    }

    fun updateIsQuickLogin(newValue: Boolean) {
        isQuickLogin.value = newValue
    }

    fun getMyFavorite(): Flow<List<Long>> {
        return flowOf(myFavorites)
    }

    fun addToFavorite(productId: Long) {
        myFavorites.add(productId)
    }

    fun removeFromMyFavorite(productId: Long) {
        myFavorites.remove(productId)
    }

    fun getAllPromotions(): Flow<List<Int>> {
        return flowOf(promotions)
    }

    // done
    fun getAllCategories(): Flow<List<Category>> {
        categories.clear()
        runBlocking {
            try {
                val response = ApiConfig().getApiService().getCategories();
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.data?.forEach() { it ->
                        categories.add(
                            Category(
                                it.id,
                                it.name,
                                it.image
                            )
                        )
                    }
                }
            } catch (_: Exception) {

            }
        }
        return flowOf(categories)
    }

    // done
    fun getSpecialSelection(): Flow<List<ProductItem>> {
        specialSelection.clear()
        runBlocking {
            try {
                val response = ApiConfig().getApiService().getItems();
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.data?.forEach() { it ->
                        specialSelection.add(ProductItem(
                            it.id.toLong(),
                            it.image,
                            it.name,
                            it.price.toDouble(),
                            it.category_id,
                            it.description,
                            it.image
                        ))
                    }
                }
            } catch (_: Exception) {

            }
        }
        return flowOf(specialSelection)
    }

    // done
    fun getProductByCategoryId(categoryId: Int): Flow<List<ProductItem>> {
        productsByCategoryId.clear()
        runBlocking {
            try {
                val response = ApiConfig().getApiService().getItems();
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.data?.forEach() { it ->
                        if (it.category_id == categoryId) {
                            productsByCategoryId.add(ProductItem(
                                it.id.toLong(),
                                it.image,
                                it.name,
                                it.price.toDouble(),
                                it.category_id,
                                it.description,
                                it.image
                            ))
                        }
                    }
                }
            } catch (_: Exception) {

            }
        }
        return flowOf(productsByCategoryId)
    }

    fun getProductFavorites(): Flow<List<ProductItem>> {
        productsFavorites.clear()
        DataSource.products().filter {
            myFavorites.contains(it.id)
        }.forEach { product ->
            productsFavorites.add(product)
        }

        return flowOf(productsFavorites)
    }

    fun addProductToCart(product: ProductItem, total: Int) {
        runBlocking {
            ApiConfig().getApiService().addItemToCart(token, product.id.toInt(), total);
        }
        reloadCart();
    }

    fun updateProductInCart(productId: Long, total: Int): Flow<Boolean> {
        runBlocking {
            ApiConfig().getApiService().updateCartItem(token, productId.toInt(), total);
        }
        reloadCart();
        return flowOf(true)
    }

    fun removeProductFromCart(productId: Long): Flow<Boolean> {
        runBlocking {
            ApiConfig().getApiService().deleteCartItem(token, productId.toInt());
        }
        reloadCart();
        return flowOf(true)
    }

    fun reloadCart() {
        runBlocking {
            try {
                val response = ApiConfig().getApiService().getItemsFromCart(token);
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.status == 200) {
                            responseBody.data.forEach() { it ->
                                orders.add(OrderItem(ProductItem(
                                    it.item.id.toLong(),
                                    it.item.image,
                                    it.item.name,
                                    it.item.price.toDouble(),
                                    it.item.category_id,
                                    it.item.description,
                                    it.item.image
                                ), it.quantity))
                            }
                        }
                    }
                }
            } catch (_: Exception) {

            }
        }
    }

    fun getAllProductInCart(): Flow<List<OrderItem>> {
        orders.clear()
        reloadCart()
        return flowOf(orders)
    }

    fun removeAllProductsFromCart(): Flow<Boolean> {
        runBlocking {
            try {
                val response = ApiConfig().getApiService().getItemsFromCart(token);
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.status == 200) {
                            responseBody.data.forEach() { it ->
                                ApiConfig().getApiService().deleteCartItem(token, it.item.id);
                            }
                        }
                    }
                }
            } catch (_: Exception) {

            }
        }
        reloadCart()
        return flowOf(true)
    }

    fun getOrderById(productId: Long): List<OrderItem> {
        return orders.filter {
            it.item.id == productId
        }
    }

    companion object {
        @Volatile
        private var instance: KantinHBRepository? = null

        fun getInstance(): KantinHBRepository =
            instance ?: synchronized(this) {
                KantinHBRepository().apply {
                    instance = this
                }
            }
    }

    // done
    suspend fun Login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = ApiConfig().getApiService().loginUser(email, password);
            if (response.isSuccessful) {
                val responseBody = response.body()
                val cookieList = response.headers().values("Set-Cookie")
                token = cookieList[0]
                    .split(";".toRegex())
                    .dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0]
                    //.split("=")[1]
                Result.success(responseBody!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun Register(name: String, email: String, password: String): Result<RegisterResponse> {
        return try {
            val response = ApiConfig().getApiService().registerUser(name, email, password);
            if (response.isSuccessful) {
                val responseBody = response.body()
                Result.success(responseBody!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun Checkout(method: String, status: String): Result<CheckoutResponse> {
        return try {
            val response = ApiConfig().getApiService().checkoutCart(token, method, status);
            if (response.isSuccessful) {
                val responseBody = response.body()
                Result.success(responseBody!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

