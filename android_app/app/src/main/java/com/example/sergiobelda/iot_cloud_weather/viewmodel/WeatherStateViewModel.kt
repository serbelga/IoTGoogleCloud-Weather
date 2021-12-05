package com.example.sergiobelda.iot_cloud_weather.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStateLiveData
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStatesListLiveData
import com.example.sergiobelda.iot_cloud_weather.repository.FirestoreWeatherRepository
import com.example.sergiobelda.iot_cloud_weather.repository.WeatherRepository

class WeatherStateViewModel : ViewModel() {
    private val repository: WeatherRepository = FirestoreWeatherRepository()

    fun getWeatherState(deviceId: String): WeatherStateLiveData {
        return repository.getWeatherState(deviceId)
    }

    fun getWeatherStates(deviceId: String): WeatherStatesListLiveData {
        return repository.getWeatherStates(deviceId)
    }
}
