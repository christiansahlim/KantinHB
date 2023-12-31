package com.rpll.kantinhb.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpll.kantinhb.data.repository.KantinHBRepository
import com.rpll.kantinhb.model.Category
import com.rpll.kantinhb.model.ProductItem
import com.rpll.kantinhb.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
private val repository: KantinHBRepository
) : ViewModel() {
    private val _promotionsUiState: MutableStateFlow<UiState<List<Int>>> = MutableStateFlow(UiState.Loading)
    val promotionsUiState: StateFlow<UiState<List<Int>>>
    get() = _promotionsUiState

    private val _categoriesUiState: MutableStateFlow<UiState<List<Category>>> = MutableStateFlow(
        UiState.Loading)
    val categoriesUiState: StateFlow<UiState<List<Category>>>
    get() = _categoriesUiState

    private val _selectedProductUiState: MutableStateFlow<UiState<List<ProductItem>>> = MutableStateFlow(
        UiState.Loading)
    val selectedProductUiState: StateFlow<UiState<List<ProductItem>>>
    get() = _selectedProductUiState

    private val _favoriteUiState: MutableStateFlow<UiState<List<Long>>> = MutableStateFlow(
        UiState.Loading)
    val favoriteUiState: StateFlow<UiState<List<Long>>>
    get() = _favoriteUiState

    fun addToFavorite(productId: Long) {
        viewModelScope.launch {
            repository.addToFavorite(productId)
            getMyFavorites()
        }
    }

    fun removeFromFavorite(productId: Long){
        viewModelScope.launch {
            repository.removeFromMyFavorite(productId)
            getMyFavorites()
        }
    }

    fun getMyFavorites() {
        viewModelScope.launch {
            repository.getMyFavorite()
                .catch {
                    _favoriteUiState.value = UiState.Error(it.message.toString())
                }
                .collect { items ->
                    _favoriteUiState.value = UiState.Success(items)
                }
        }
    }

    fun getAllPromotions() {
        viewModelScope.launch {
            repository.getAllPromotions()
                .catch {
                    _promotionsUiState.value = UiState.Error(it.message.toString())
                }
                .collect { items ->
                    _promotionsUiState.value = UiState.Success(items)
                }
        }
    }

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

    fun getSelectedProduct() {
        viewModelScope.launch {
            repository.getSpecialSelection()
                .catch {
                    _selectedProductUiState.value = UiState.Error(it.message.toString())
                }
                .collect { items ->
                    _selectedProductUiState.value = UiState.Success(items)
                }
        }
    }
}