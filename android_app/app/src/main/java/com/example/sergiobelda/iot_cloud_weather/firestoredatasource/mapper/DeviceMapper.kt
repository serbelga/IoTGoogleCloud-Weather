package com.example.sergiobelda.iot_cloud_weather.firestoredatasource.mapper

import com.example.sergiobelda.iot_cloud_weather.firestoredatasource.model.DeviceFirestore
import com.example.sergiobelda.iot_cloud_weather.model.Device

object DeviceMapper {

    fun DeviceFirestore.map(): Device =
        Device(id = id)
}
