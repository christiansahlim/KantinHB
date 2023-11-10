package com.rpll.kantinhb.ui.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpll.kantinhb.data.KantinHBRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(private val repository: KantinHBRepository) : ViewModel() {
    // MutableState untuk menyimpan visibilitas password
    private val _passwordVisibility = MutableStateFlow(false)
    val passwordVisibility: StateFlow<Boolean> = _passwordVisibility

    // MutableState untuk fungsi remember me pada password
    private val _passwordRemember = MutableStateFlow(false)
    val passwordRemember: StateFlow<Boolean> = _passwordRemember

    // MutableState untuk menyimpan nilai teks email dan password
    private val _emailValue = MutableStateFlow("")
    val emailValue: StateFlow<String> = _emailValue

    private val _passwordValue = MutableStateFlow("")
    val passwordValue: StateFlow<String> = _passwordValue

    fun togglePasswordVisibility() {
        _passwordVisibility.value = !_passwordVisibility.value
    }

    fun toggleRememberMe() {
        _passwordRemember.value = !_passwordRemember.value
    }

    fun setEmailValue(email: String) {
        _emailValue.value = email
    }

    fun setPasswordValue(password: String) {
        _passwordValue.value = password
    }

    fun performLogin() {
        val email = emailValue.value
        val password = passwordValue.value

        if (isValidEmail(email) && isValidPassword(password)) {
            // Implement login logic here
            // You can call your repository to handle the login process
            // repository.login(email, password)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        // Implement email validation logic here
        // Return true if the email is valid, otherwise return false
        return true
    }

    private fun isValidPassword(password: String): Boolean {
        // Implement password validation logic here
        // Return true if the password is valid, otherwise return false
        return true
    }
}
