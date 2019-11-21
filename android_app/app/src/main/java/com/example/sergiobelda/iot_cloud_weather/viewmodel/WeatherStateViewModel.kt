package com.example.sergiobelda.iot_cloud_weather.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStateLiveData
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStatesListLiveData
import com.example.sergiobelda.iot_cloud_weather.repository.FirestoreWeatherRepository
import com.example.sergiobelda.iot_cloud_weather.repository.WeatherRepository

class WeatherStateViewModel : ViewModel() {
    private val repository: WeatherRepository = FirestoreWeatherRepository()

    fun getWeatherState(deviceId: String): WeatherStateLiveData {
        val liveData = repository.getWeatherState(deviceId)
        return liveData
    }

    fun getWeatherStates(deviceId: String): WeatherStatesListLiveData {
        val liveData = repository.getWeatherStates(deviceId)
        return liveData
    }
}

