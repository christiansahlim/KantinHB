package com.rpll.kantinhb.ui.screen.auth.register

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rpll.kantinhb.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rpll.kantinhb.data.repository.KantinHBRepository
import com.rpll.kantinhb.di.Injection
import com.rpll.kantinhb.navigation.KantinHBScreen
import com.rpll.kantinhb.ui.ViewModelFactory
import com.rpll.kantinhb.utils.BackPress
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    // MutableState untuk menyimpan nilai teks email dan password
    val emailValue = remember { mutableStateOf(TextFieldValue()) }
    val passwordValue = remember { mutableStateOf(TextFieldValue()) }
    // MutableState untuk menyimpan visibilitas password
    val passwordVisibility = remember { mutableStateOf(false) }

    //Screen Config
    val configuration = LocalConfiguration.current

    //Back press exit attributes
    var showToast by remember { mutableStateOf(false) }

    var backPressState by remember { mutableStateOf<BackPress>(BackPress.Idle) }
    val context = LocalContext.current

    if (showToast) {
        Toast.makeText(context, "Press again to exit", Toast.LENGTH_SHORT).show()
        showToast = false
    }

    DisposableEffect(Unit) {
        onDispose {
            // Clean up resources when the effect is removed
        }
    }

    LaunchedEffect(key1 = backPressState) {
        if (backPressState == BackPress.InitialTouch) {
            delay(2000)
            backPressState = BackPress.Idle
        }
    }

    BackHandler(backPressState == BackPress.Idle) {
        backPressState = BackPress.InitialTouch
        showToast = true
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Image",
                    modifier = Modifier
                        .padding(top = 0.dp)
                        .size(150.dp)
                )
            }
            Text(
                text = "Register",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 32.sp),
                modifier = Modifier.padding(bottom = 24.dp)
            )
            val registerText = buildAnnotatedString {
                append(
                    "If you have an account \n" +
                            "You Can "
                )
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                    )
                ) {
                    append("Login Here!")
                }
            }
            ClickableText(
                text = registerText,
                onClick = {
                navController.navigate(KantinHBScreen.LoginScreen.route)
                },
                style = TextStyle(fontSize = 20.sp),
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Text(
                text = "Email",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_email),
                    contentDescription = "Email Logo",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = emailValue.value,
                    onValueChange = { newValue ->
                        emailValue.value = newValue
                    },
                    placeholder = {
                        Text(
                            text = "Enter your email address",
                            style = TextStyle(color = Color.Black, fontSize = 20.sp)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = "Username",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_user),
                    contentDescription = "username Logo",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = passwordValue.value,
                    onValueChange = { newValue ->
                        passwordValue.value = newValue
                    },
                    placeholder = {
                        Text(
                            text = "Enter your Username",
                            style = TextStyle(color = Color.Black, fontSize = 20.sp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            Text(
                text = "Password",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_kunci),
                    contentDescription = "password Logo",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = passwordValue.value,
                    onValueChange = { newValue ->
                        passwordValue.value = newValue
                    },
                    placeholder = {
                        Text(
                            text = "Enter your Password",
                            style = TextStyle(color = Color.Black, fontSize = 20.sp)
                        )
                    },
                    modifier = Modifier.weight(1f),
                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (passwordVisibility.value) R.drawable.logo_visible else R.drawable.logo_invisible
                        Image(
                            painter = painterResource(image),
                            contentDescription = "Toggle Password Visibility",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    passwordVisibility.value = !passwordVisibility.value
                                }
                        )
                    }
                )
            }

            Text(
                text = "Confirm Password",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_kunci),
                    contentDescription = "confirm password Logo",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = passwordValue.value,
                    onValueChange = { newValue ->
                        passwordValue.value = newValue
                    },
                    placeholder = {
                        Text(
                            text = "Confirm your Password",
                            style = TextStyle(color = Color.Black, fontSize = 20.sp)
                        )
                    },
                    modifier = Modifier.weight(1f),
                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (passwordVisibility.value) R.drawable.logo_visible else R.drawable.logo_invisible
                        Image(
                            painter = painterResource(image),
                            contentDescription = "Toggle Password Visibility",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    passwordVisibility.value = !passwordVisibility.value
                                }
                        )
                    }
                )
            }

            Button(
                onClick = {
                    navController.navigate(KantinHBScreen.LoginScreen.route)
                },
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
                contentPadding = PaddingValues()
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "Register",
                        color = Color.White,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    val repository = KantinHBRepository.getInstance()
    val viewModel = RegisterViewModel(repository)
    RegisterScreen(navController = rememberNavController(), viewModel = viewModel)
}
