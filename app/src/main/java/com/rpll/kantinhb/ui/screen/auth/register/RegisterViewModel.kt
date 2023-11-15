package com.rpll.kantinhb.ui.screen.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpll.kantinhb.data.repository.KantinHBRepository
import com.rpll.kantinhb.ui.common.UiState
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: KantinHBRepository) : ViewModel() {

    // MutableState untuk menyimpan pesan kesalahan registrasi
    private var _errorText by mutableStateOf<String?>(null)
    val errorText: String? get() = _errorText

    // Fungsi untuk melakukan registrasi
    fun performRegister(email: String, username: String, password: String) {
        viewModelScope.launch {
//            try {
//                val result = repository.register(email, username, password)
//
//                if (result is UiState.Success) {
//                    // Registrasi berhasil
//                } else if (result is UiState.Error) {
//                    // Registrasi gagal, atur pesan kesalahan
//                    _errorText = "Registration failed. ${result.errorMessage ?: "Unknown error"}"
//                }
//            } catch (e: Exception) {
//                // Tangani exception jika terjadi kesalahan selama proses registrasi
//                _errorText = "An unexpected error occurred."
//            }
//        }
        }
    }
}
