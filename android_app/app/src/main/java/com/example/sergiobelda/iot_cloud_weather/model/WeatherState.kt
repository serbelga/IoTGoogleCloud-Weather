package com.example.sergiobelda.iot_cloud_weather.model

import com.example.sergiobelda.iot_cloud_weather.util.FormatNumber

data class WeatherState(
    var temperature: Double,
    var humidity: Double,
    var lastConnection: String,
    var online: Boolean
) {
    fun getTemperatureString(): String {
        val temperature = FormatNumber.getFormatNumberString(temperature)
        return "$temperature°"
    }

    fun getHumidityString(): String {
        val humidity = FormatNumber.getFormatNumberString(humidity)
        return "$humidity%"
    }
}
