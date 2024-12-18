package dev.sergiobelda.iot.cloud.weather.firestoredatasource.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class DeviceWeatherStateFirestore(
    @JvmField
    @PropertyName("state")
    var weather: WeatherFirestore? = null,
    var timestamp: Timestamp? = null,
    var online: Boolean? = null,
)
