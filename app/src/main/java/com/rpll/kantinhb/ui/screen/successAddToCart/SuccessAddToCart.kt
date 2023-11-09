package com.rpll.kantinhb.ui.screen.successAddToCart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpll.kantinhb.data.KantinHBRepository
import com.rpll.kantinhb.model.Category
import com.rpll.kantinhb.model.ProductItem
import com.rpll.kantinhb.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: KantinHBRepository
) : ViewModel() {
    private val _categoriesUiState: MutableStateFlow<UiState<List<Category>>> = MutableStateFlow(
        UiState.Loading
    )
    val categoriesUiState: StateFlow<UiState<List<Category>>>
        get() = _categoriesUiState

    private val _productUiState: MutableStateFlow<UiState<List<ProductItem>>> = MutableStateFlow(
        UiState.Loading
    )
    val productUiState: StateFlow<UiState<List<ProductItem>>>
        get() = _productUiState

    fun getAllCategories() {
        viewModelScope.launch {
            repository.getAllCategories()
                .catch {
                    _categoriesUiState.value = UiState.Error(it.message.toString())
                }
                .collect { items ->
                    _categoriesUiState.value = UiState.Success(items)
                }
        }
    }

    fun getProductByCategoryId(id: Int) {
        viewModelScope.launch {
            repository.getProductByCategoryId(id)
                .catch {
                    _productUiState.value = UiState.Error(it.message.toString())
                }
                .collect { items ->
                    _productUiState.value = UiState.Success(items)
                }
        }
    }
}