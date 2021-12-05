package com.example.sergiobelda.iot_cloud_weather.util

import kotlin.math.roundToInt

class FormatNumber {
    fun getFormatNumberString(number: Double): String {
        return ((number * 100.0).roundToInt() / 100.0).toString()
    }

    fun getFormatNumberDouble(number: Double): Double {
        return (number * 100.0).roundToInt() / 100.0
    }
}
