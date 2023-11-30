package com.rpll.kantinhb.ui.screen.payment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.rpll.kantinhb.data.repository.KantinHBRepository
import com.rpll.kantinhb.model.OrderItem
import com.rpll.kantinhb.navigation.KantinHBScreen
import com.rpll.kantinhb.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PaymentViewModel(
private val repository: KantinHBRepository
): ViewModel() {
    private val _productUiState: MutableStateFlow<UiState<List<OrderItem>>> = MutableStateFlow(
        UiState.Loading
    )
    val productUiState: StateFlow<UiState<List<OrderItem>>>
        get() = _productUiState

    private val _updateUiState: MutableStateFlow<UiState<Boolean>> = MutableStateFlow(
        UiState.Loading
    )
    val updateUiState: StateFlow<UiState<Boolean>>
        get() = _updateUiState

    private val _removeUiState: MutableStateFlow<UiState<Boolean>> = MutableStateFlow(
        UiState.Loading
    )
    val removeUiState: StateFlow<UiState<Boolean>>
        get() = _removeUiState

    val totalPriceInCart = mutableStateOf(0.0)

    val cashClicked = mutableStateOf(false)
    val ewalletClicked = mutableStateOf(false)

    fun getProductsInPayment() {
        viewModelScope.launch {
            repository.getAllProductInCart()
                .catch {
                    _productUiState.value = UiState.Error(it.message.toString())
                }
                .collect { items ->
                    for (order in items) {
                        totalPriceInCart.value += order.item.price * order.count
                    }
                    _productUiState.value = UiState.Success(items)
                }
        }
    }

    fun updateProductInPayment(productId: Long, total: Int) {
        viewModelScope.launch {
            repository.updateProductInCart(productId = productId, total = total)
                .catch {
                    _updateUiState.value = UiState.Error(it.message.toString())
                }
                .collect { items ->
                    //Update the latest total price in cart
                    totalPriceInCart.value = 0.0
                    getProductsInPayment()

                    _updateUiState.value = UiState.Success(items)
                }
        }
    }

    fun removeProductInPayment(productId: Long) {
        viewModelScope.launch {
            repository.removeProductFromCart(productId = productId)
                .catch {
                    _removeUiState.value = UiState.Error(it.message.toString())
                }
                .collect { items ->
                    //Update the latest total price in cart
                    totalPriceInCart.value = 0.0
                    getProductsInPayment()

                    _removeUiState.value = UiState.Success(items)
                }
        }
    }

    fun checkoutCart(navController: NavController) {
        viewModelScope.launch {
            val method = if (cashClicked.value) "Cash" else (if (ewalletClicked.value) "E-wallet" else "Unknown");
            repository.Checkout(method, "Success")
            navController.navigate(KantinHBScreen.SuccessPayment.route)
        }
    }
}
