package com.rpll.kantinhb.ui.screen.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rpll.kantinhb.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.rpll.kantinhb.data.KantinHBRepository

@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    val emailValue = loginViewModel.emailValue.collectAsState()
    val passwordValue = loginViewModel.passwordValue.collectAsState()
    val passwordVisibility = loginViewModel.passwordVisibility.collectAsState()
    val passwordRemember = loginViewModel.passwordRemember.collectAsState()


}

@Preview
@Composable
fun LoginScreenPreview() {
    val repository = KantinHBRepository.getInstance()
    val loginViewModel = LoginViewModel(repository)
    LoginScreen(loginViewModel)
}
