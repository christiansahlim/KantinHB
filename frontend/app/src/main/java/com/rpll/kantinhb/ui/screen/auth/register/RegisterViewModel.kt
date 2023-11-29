package com.rpll.kantinhb.ui.screen.auth.register

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

class RegisterViewModel(private val repository: KantinHBRepository) : ViewModel() {

    // MutableState untuk menyimpan pesan kesalahan login
    private var _errorText by mutableStateOf<String?>(null)
    val errorText: String? get() = _errorText

    // MutableState untuk menyimpan status login
    private var _isLoading by mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading

    // Fungsi untuk melakukan login
    fun performRegister(
        email: String,
        name: String,
        password: String,
        navController: NavController
    ) {
        _isLoading = true;
        viewModelScope.launch {
            try {
                val result = repository.Register(email, name, password).getOrNull()
                if (result != null) {
                    if (result.status == 200) {
                        navController.navigate(KantinHBScreen.LoginScreen.route)
                    }

                } else {
                    _errorText = "Register failed Unknown error"
                }
            } catch (e: Exception) {
                _errorText = "An unexpected error occurred."
            }
            _isLoading = false;
        }
    }
}
