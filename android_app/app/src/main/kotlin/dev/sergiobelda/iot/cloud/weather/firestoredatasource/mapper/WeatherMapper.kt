package dev.sergiobelda.iot.cloud.weather.firestoredatasource.mapper

import dev.sergiobelda.iot.cloud.weather.firestoredatasource.model.WeatherFirestore
import dev.sergiobelda.iot.cloud.weather.model.Weather

object WeatherMapper {

    fun WeatherFirestore.map(): Weather =
        Weather(
            temperature = temperature,
            humidity = humidity,
        )
}
