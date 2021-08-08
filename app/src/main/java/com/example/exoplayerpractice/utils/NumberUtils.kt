package com.example.exoplayerpractice.utils

import java.text.DecimalFormat

private val decimalFormat = DecimalFormat("00")

val Long.toPlaybackTime: String
    get() {
        val timeInSecond = this / 1000
        val second = timeInSecond % 60
        val minute = (timeInSecond - second) / 60
        return "$minute:${decimalFormat.format(second)}"
    }