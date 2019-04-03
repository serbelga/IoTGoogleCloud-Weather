package com.example.sergiobelda.iot_cloud_weather.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStateLiveData
import com.example.sergiobelda.iot_cloud_weather.repository.FirestoreWeatherStateRepository
import com.example.sergiobelda.iot_cloud_weather.repository.WeatherStateRepository

class WeatherStateViewModel : ViewModel() {
    private val repository: WeatherStateRepository = FirestoreWeatherStateRepository()
    fun getWeatherStateLiveData(deviceId: String): WeatherStateLiveData {
        val liveData = repository.getWeatherStateLiveData(deviceId)
        return liveData
    }
}

