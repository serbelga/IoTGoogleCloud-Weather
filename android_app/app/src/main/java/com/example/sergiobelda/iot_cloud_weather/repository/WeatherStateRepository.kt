package com.example.sergiobelda.iot_cloud_weather.repository

import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStateLiveData

interface WeatherStateRepository {
    fun getWeatherStateLiveData(deviceId: String): WeatherStateLiveData
}