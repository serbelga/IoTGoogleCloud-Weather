package dev.sergiobelda.iot.cloud.weather.firestoredatasource.mapper

import dev.sergiobelda.iot.cloud.weather.firestoredatasource.model.DeviceFirestore
import dev.sergiobelda.iot.cloud.weather.model.Device

object DeviceMapper {

    fun DeviceFirestore.map(): Device =
        Device(id = id)
}
