package com.example.sergiobelda.iot_cloud_weather.repository

import com.example.sergiobelda.iot_cloud_weather.livedata.DevicesLiveData
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStateLiveData
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStatesLiveData
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreWeatherRepository : WeatherRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val collectionPath = "device-configs"

    override fun getDevices(): DevicesLiveData {
        val ref = firestore.collection(collectionPath)
        return DevicesLiveData(ref)
    }

    override fun getWeatherState(deviceId: String): WeatherStateLiveData {
        val ref = firestore.collection(collectionPath).document(deviceId)
        return WeatherStateLiveData(ref)
    }

    override fun getWeatherStates(deviceId: String): WeatherStatesLiveData {
        val ref = firestore.collection(collectionPath).document(deviceId)
        return WeatherStatesLiveData(ref)
    }
}