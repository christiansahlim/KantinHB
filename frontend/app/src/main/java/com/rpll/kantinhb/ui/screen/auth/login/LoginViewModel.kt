package com.rpll.kantinhb.ui.screen.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpll.kantinhb.data.repository.KantinHBRepository
import com.rpll.kantinhb.ui.common.UiState
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: KantinHBRepository) : ViewModel() {

    // MutableState untuk menyimpan status login
    private var _isLoggedIn by mutableStateOf(false)
    val isLoggedIn: Boolean get() = _isLoggedIn

    // MutableState untuk menyimpan pesan kesalahan login
    private var _errorText by mutableStateOf<String?>(null)
    val errorText: String? get() = _errorText

    // Fungsi untuk melakukan login
    fun performLogin(email: String, password: String) {
        viewModelScope.launch {
//            try {
//                val result = repository.login(email, password)
//
//                if (result is UiState.Success) {
//                    // Login berhasil
//                    _isLoggedIn = true
//                } else if (result is UiState.Error) {
//                    // Login gagal, atur pesan kesalahan
//                    _errorText = "Login failed. ${result.errorMessage ?: "Unknown error"}"
//                }
//            } catch (e: Exception) {
//                // Tangani exception jika terjadi kesalahan selama proses login
//                _errorText = "An unexpected error occurred."
//            }
        }
    }
}

