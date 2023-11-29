package com.rpll.kantinhb.data.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.rpll.kantinhb.model.Category
import com.rpll.kantinhb.model.OrderItem
import com.rpll.kantinhb.model.ProductItem
import com.rpll.kantinhb.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import com.rpll.kantinhb.data.api.ApiConfig
import com.rpll.kantinhb.data.response.LoginResponse
import com.rpll.kantinhb.data.source.DataSource
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

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

    fun getAllCategories(): Flow<List<Category>> {
        return flowOf(categories)
    }

    fun getSpecialSelection(): Flow<List<ProductItem>> {
        return flowOf(specialSelection)
    }

    fun getProductByCategoryId(categoryId: Int): Flow<List<ProductItem>> {
        Log.e("Product By ID", "CALLED IN HERE")
        productsByCategoryId.clear()
        DataSource.products().filter {
            it.category_id == categoryId
        }.forEach { product ->
            productsByCategoryId.add(product)
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
        orders.add(
            OrderItem(
                item = product,
                count = total
            )
        )
    }

    fun updateProductInCart(productId: Long, total: Int): Flow<Boolean> {
        val index = orders.indexOfFirst { it.item.id == productId }
        val result = if (index >= 0) {
            val order = orders[index]
            orders[index] = order.copy(item = order.item, count = total)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun removeProductFromCart(productId: Long): Flow<Boolean> {
        val index = orders.indexOfFirst { it.item.id == productId }
        val result = if (index >= 0) {
            orders.removeAt(index)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAllProductInCart(): Flow<List<OrderItem>> {
        return flowOf(orders)
    }

    fun removeAllProductsFromCart(): Flow<Boolean> {
        orders.clear()
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

    suspend fun Login(email: String, password: String): Result<LoginResponse> {
        return try {
            Log.d("TEST TAG", email)
            Log.d("TEST TAG", password)

            val response = ApiConfig().getApiService().loginUser(email, password);
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

    // Fungsi untuk melakukan login
//    fun Login(email: String, password: String): Flow<UiState<Boolean>> {
//        return performLoginFunction (email, password) { result ->
//            if (result is UiState.Success) {
//                _isLoggedIn = true
//            } else if (result is UiState.Error) {
//                _errorText = "Login failed. ${result.errorMessage ?: "Unknown error"}"
//            }
//        }
//    }
//
//    // Fungsi untuk melakukan registrasi
//    fun Register(email: String, username: String, password: String): Flow<UiState<Boolean>> {
//        return performRegisterFunction(email, username, password) { result ->
//            if (result is UiState.Success) {
//                performLoginFunction(email, password) { loginResult ->
//                    if (loginResult is UiState.Error) {
//                        _errorText = "Login failed. ${loginResult.errorMessage ?: "Unknown error"}"
//                    }
//                }
//            } else if (result is UiState.Error) {
//                _errorText = "Registration failed. ${result.errorMessage ?: "Unknown error"}"
//            }
//        }
//    }
}

