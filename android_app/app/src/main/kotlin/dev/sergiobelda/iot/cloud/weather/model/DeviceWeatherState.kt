package dev.sergiobelda.iot.cloud.weather.model

import java.io.Serializable

data class DeviceWeatherState(
    var weather: Weather?,
    var lastConnection: String?,
    var online: Boolean?,
) : Serializable
