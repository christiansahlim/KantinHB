package com.rpll.kantinhb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rpll.kantinhb.navigation.NavigationBuilder
import com.rpll.kantinhb.ui.theme.KantinHBTheme

class KantinHBApp: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KantinHBTheme {
                NavigationBuilder()
            }
        }
    }
}