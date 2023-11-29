package com.rpll.kantinhb.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id : String,
    var username : String,
    var token : String
): Parcelable