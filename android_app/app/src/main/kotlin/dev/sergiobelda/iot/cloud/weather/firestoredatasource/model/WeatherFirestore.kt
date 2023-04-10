package dev.sergiobelda.iot.cloud.weather.firestoredatasource.model

data class WeatherFirestore(
    var temperature: Double? = null,
    var humidity: Double? = null,
)
