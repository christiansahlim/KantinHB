package com.rpll.kantinhb.ui.screen.auth.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.rpll.kantinhb.data.repository.KantinHBRepository
import com.rpll.kantinhb.navigation.KantinHBScreen
import com.rpll.kantinhb.ui.common.UiState
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: KantinHBRepository) : ViewModel() {

    // MutableState untuk menyimpan status login
    private var _isLoggedIn by mutableStateOf(false)
    val isLoggedIn: Boolean get() = _isLoggedIn

    // MutableState untuk menyimpan pesan kesalahan login
    private var _errorText by mutableStateOf<String?>(null)
    val errorText: String? get() = _errorText

    // MutableState untuk menyimpan status login
    private var _isLoading by mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading

    // Fungsi untuk melakukan login
    fun performLogin(email: String, password: String, navController: NavController) {
        _isLoading = true;
        viewModelScope.launch {
            try {
                val result = repository.Login(email, password).getOrNull()
                if (result != null) {
                    if (result.status == 200) {
                        if (!result.data.admin) {
                            _isLoggedIn = true
                            navController.navigate(KantinHBScreen.HomeScreen.route)
                        }
                    }
                } else {
                    _errorText = "Login failed Unknown error"
                }
            } catch (e: Exception) {
                _errorText = "An unexpected error occurred."
            }
            _isLoading = false;
        }
    }
}

