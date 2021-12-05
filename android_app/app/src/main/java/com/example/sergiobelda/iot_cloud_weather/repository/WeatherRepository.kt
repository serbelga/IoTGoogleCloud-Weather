package com.example.sergiobelda.iot_cloud_weather.repository

import com.example.sergiobelda.iot_cloud_weather.livedata.DevicesListLiveData
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStateLiveData
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStatesListLiveData

interface WeatherRepository {

    fun getWeatherState(deviceId: String): WeatherStateLiveData

    fun getDevices(): DevicesListLiveData

    fun getWeatherStates(deviceId: String): WeatherStatesListLiveData
}
