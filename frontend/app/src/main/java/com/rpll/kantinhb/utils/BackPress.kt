package com.rpll.kantinhb.utils

sealed class BackPress {
    object Idle : BackPress()
    object InitialTouch : BackPress()
}