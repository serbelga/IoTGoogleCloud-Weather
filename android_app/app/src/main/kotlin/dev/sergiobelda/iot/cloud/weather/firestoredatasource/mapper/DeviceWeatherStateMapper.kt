package dev.sergiobelda.iot.cloud.weather.firestoredatasource.mapper

import android.text.format.DateFormat
import dev.sergiobelda.iot.cloud.weather.firestoredatasource.mapper.WeatherMapper.map
import dev.sergiobelda.iot.cloud.weather.firestoredatasource.model.DeviceWeatherStateFirestore
import dev.sergiobelda.iot.cloud.weather.model.DeviceWeatherState

object DeviceWeatherStateMapper {

    fun DeviceWeatherStateFirestore.map(): DeviceWeatherState =
        DeviceWeatherState(
            weather = weather?.map(),
            lastConnection = DateFormat.format("dd-MM-yyyy HH:mm:ss", timestamp?.toDate()).toString(),
            online = online,
        )
}
