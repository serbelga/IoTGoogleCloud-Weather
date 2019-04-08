package com.example.sergiobelda.iot_cloud_weather.util

class FormatNumber {
    fun getFormatNumberString(number: Double) : String {
        return (Math.round(number * 100.0) / 100.0).toString()
    }

    fun getFormatNumberDouble(number: Double) : Double {
        return Math.round(number * 100.0) / 100.0
    }
}