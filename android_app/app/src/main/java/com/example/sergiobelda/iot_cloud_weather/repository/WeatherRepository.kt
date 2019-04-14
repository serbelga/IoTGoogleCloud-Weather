package com.example.sergiobelda.iot_cloud_weather.repository

import com.example.sergiobelda.iot_cloud_weather.livedata.DevicesLiveData
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStateLiveData

interface WeatherRepository {
    fun getWeatherStateLiveData(deviceId: String): WeatherStateLiveData

    fun getDevices(): DevicesLiveData
}