package com.rpll.kantinhb.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rpll.kantinhb.R

@Composable
fun Loader(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader_blue))
    Box(modifier = Modifier.fillMaxWidth()){
        LottieAnimation(
            composition = composition,
            modifier = modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            iterations = LottieConstants.IterateForever,
        )
    }
}