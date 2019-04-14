package com.example.sergiobelda.iot_cloud_weather.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStateLiveData
import com.example.sergiobelda.iot_cloud_weather.repository.FirestoreWeatherRepository
import com.example.sergiobelda.iot_cloud_weather.repository.WeatherRepository

class WeatherStateViewModel : ViewModel() {
    private val repository: WeatherRepository = FirestoreWeatherRepository()
    fun getWeatherStateLiveData(deviceId: String): WeatherStateLiveData {
        val liveData = repository.getWeatherStateLiveData(deviceId)
        return liveData
    }
}

