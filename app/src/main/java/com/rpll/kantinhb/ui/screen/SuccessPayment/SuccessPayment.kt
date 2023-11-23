package com.rpll.kantinhb.ui.screen.SuccessPayment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rpll.kantinhb.R
import com.rpll.kantinhb.navigation.KantinHBScreen
import com.rpll.kantinhb.ui.theme.VividBlue_100
import kotlinx.coroutines.delay

@Composable
fun SuccessPayment(
    navController: NavController,
){

    LaunchedEffect(Unit) {
        delay(1000L)
        navController.navigate(KantinHBScreen.HomeScreen.route)
        navController.currentBackStackEntry?.arguments?.putBoolean("addedNewItem",true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.payment_success_blue))
        Box(modifier = Modifier.size(200.dp)){
            LottieAnimation(
                composition = composition,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
            )
        }
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text =  "Payment Success",
            style = TextStyle(
                fontSize = 24.sp,
                color = VividBlue_100,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
    }
}