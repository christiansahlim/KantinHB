//package com.rpll.kantinhb.ui.screen.auth.register
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.ClickableText
//import androidx.compose.material.Button
//import androidx.compose.material.Text
//import androidx.compose.material.TextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.SpanStyle
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.buildAnnotatedString
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.withStyle
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.rpll.kantinhb.ui.screen.auth.register.RegisterViewModel
//import com.rpll.kantinhb.R
//import com.rpll.kantinhb.data.KantinHBRepository
//
//@Composable
//fun RegisterScreen(viewModel: RegisterViewModel) {
//    // MutableState untuk menyimpan nilai teks email, username, dan password
//    val emailValue = RegisterViewModel.emailValue.collectAsState()
//    val passwordValue = RegisterViewModel.passwordValue.collectAsState()
//    val passwordVisibility = RegisterViewModel.passwordVisibility.collectAsState()
//    val passwordRemember = RegisterViewModel.passwordRemember.collectAsState()
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .padding(32.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        contentPadding = PaddingValues(bottom = 16.dp)
//    ) {
//        item {
//            Box(
//                modifier = Modifier.fillMaxWidth(),
//                contentAlignment = Alignment.TopCenter
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.logo),
//                    contentDescription = "Image",
//                    modifier = Modifier
//                        .padding(top = 0.dp)
//                        .size(150.dp)
//                )
//            }
//            Text(
//                text = "Register",
//                style = TextStyle(fontweight = FontWeight.Bold, fontsize = 32.sp),
//                modifier = Modifier.padding(bottom = 24.dp)
//            )
//            val registerText = buildAnnotatedString {
//                append(
//                    "If you have an account \n" +
//                            "You Can "
//                )
//                withStyle(
//                    style = SpanStyle(
//                        color = Color.Blue,
//                    )
//                ) {
//                    append("Login Here!")
//                }
//            }
//            ClickableText(
//                text = registerText,
//                onClick = {
//                    // Implementasi navigasi ke layar login
//                },
//                style = TextStyle(fontsize = 20.sp),
//                modifier = Modifier.padding(bottom = 24.dp)
//            )
//            Text(
//                text = "Email",
//                style = TextStyle(
//                    fontsize = 20.sp,
//                    color = Color.Gray
//                ),
//                modifier = Modifier.padding(bottom = 20.dp)
//            )
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.padding(bottom = 24.dp)
//            ) {
//                TextField(
//                    value = emailValue,
//                    onValueChange = { emailValue = it },
//                    placeholder = {
//                        Text(
//                            text = "Enter your email address",
//                            style = TextStyle(color = Color.Black, fontsize = 20.sp)
//                        )
//                    },
//                    modifier = Modifier.fillMaxWidth()
//            }
//        }
//        Text(
//            text = "Username",
//            style = TextStyle(
//                fontsize = 20.sp,
//                color = Color.Gray
//            ),
//            modifier = Modifier.padding(bottom = 20.dp)
//        )
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(bottom = 24.dp)
//        ) {
//            TextField(
//                value = usernameValue,
//                onValueChange = { usernameValue = it },
//                placeholder = {
//                    Text(
//                        text = "Enter your Username",
//                        style = TextStyle(color = Color.Black, fontsize = 20.sp)
//                    )
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//            )
//        }
//        Text(
//            text = "Password",
//            style = TextStyle(
//                fontsize = 20.sp,
//                color = Color.Gray
//            ),
//            modifier = Modifier.padding(bottom = 20.dp)
//        )
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(bottom = 24.dp)
//        ) {
//            TextField(
//                value = passwordValue,
//                onValueChange = { passwordValue = it },
//                placeholder = {
//                    Text(
//                        text = "Enter your Password",
//                        style = TextStyle(color = Color.Black, fontsize = 20.sp)
//                    )
//                },
//                modifier = Modifier.weight(1f),
//                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
//                trailingIcon = {
//                    // Implementasi toggle password visibility
//                }
//            )
//        }
//        Button(
//            onClick = {
//                // Handle registrasi saat tombol diklik
//                viewModel.performRegistration(
//                    emailValue.text,
//                    usernameValue.text,
//                    passwordValue.text
//                )
//            },
//            modifier = Modifier
//                .padding(top = 24.dp)
//                .fillMaxWidth()
//                .height(48.dp),
//            shape = RoundedCornerShape(24.dp),
//            colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
//            contentPadding = PaddingValues()
//        ) {
//            Box(contentalignment = Alignment.Center) {
//                Text(
//                    text = "Register",
//                    color = Color.White,
//                    style = TextStyle(fontweight = FontWeight.Bold, fontsize = 18.sp),
//                    textalign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(vertical = 8.dp)
//                )
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun PreviewRegisterScreen() {
//    val repository = KantinHBRepository.getInstance()
//    val viewModel = RegisterViewModel(repository)
//    RegisterScreen(viewModel)
//}
