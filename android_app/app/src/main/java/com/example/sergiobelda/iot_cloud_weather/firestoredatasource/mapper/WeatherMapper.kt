package com.example.sergiobelda.iot_cloud_weather.firestoredatasource.mapper

import com.example.sergiobelda.iot_cloud_weather.firestoredatasource.model.WeatherFirestore
import com.example.sergiobelda.iot_cloud_weather.model.Weather

object WeatherMapper {

    fun WeatherFirestore.map(): Weather =
        Weather(
            temperature = temperature,
            humidity = humidity
        )
}
