package com.example.sergiobelda.iot_cloud_weather.repository

import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStateLiveData
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreWeatherStateRepository : WeatherStateRepository {
    private val firestore = FirebaseFirestore.getInstance()
    override fun getWeatherStateLiveData(deviceId: String): WeatherStateLiveData {
        val ref = firestore.collection("device-configs").document(deviceId)
        return WeatherStateLiveData(ref)
    }
}