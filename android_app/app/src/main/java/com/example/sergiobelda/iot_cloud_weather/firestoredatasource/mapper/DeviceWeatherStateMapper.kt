package com.example.sergiobelda.iot_cloud_weather.firestoredatasource.mapper

import android.text.format.DateFormat
import com.example.sergiobelda.iot_cloud_weather.firestoredatasource.mapper.WeatherMapper.map
import com.example.sergiobelda.iot_cloud_weather.firestoredatasource.model.DeviceWeatherStateFirestore
import com.example.sergiobelda.iot_cloud_weather.model.DeviceWeatherState

object DeviceWeatherStateMapper {

    fun DeviceWeatherStateFirestore.map(): DeviceWeatherState =
        DeviceWeatherState(
            weather = weather?.map(),
            lastConnection = DateFormat.format("dd-MM-yyyy HH:mm:ss", timestamp?.toDate()).toString(),
            online = online
        )
}
