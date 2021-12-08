package com.example.sergiobelda.iot_cloud_weather.model

import java.io.Serializable

data class DeviceWeatherState(
    var weather: Weather?,
    var lastConnection: String?,
    var online: Boolean?
) : Serializable
