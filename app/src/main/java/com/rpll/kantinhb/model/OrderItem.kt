package com.rpll.kantinhb.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderItem {
    val item: ProductItem,
    val count: Int
}: Parcelable