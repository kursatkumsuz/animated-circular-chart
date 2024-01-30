package com.kursatkumsuz.circularchart.model

import androidx.compose.ui.graphics.Color

data class ChartModel(
    val coin: String,
    val amountPrice: Float,
    val color: Color,
    var isClicked : Boolean = false
)