package com.app.catapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatItem(
    val id: String,
    val src: String
): Parcelable

