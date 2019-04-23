package com.example.sergiobelda.iot_cloud_weather.repository

import com.example.sergiobelda.iot_cloud_weather.livedata.DevicesLiveData
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStateLiveData
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStatesLiveData

interface WeatherRepository {
    fun getWeatherState(deviceId: String): WeatherStateLiveData

    fun getDevices(): DevicesLiveData

    fun getWeatherStates(deviceId: String): WeatherStatesLiveData
}