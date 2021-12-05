package com.example.sergiobelda.iot_cloud_weather.repository

import com.example.sergiobelda.iot_cloud_weather.livedata.DevicesListLiveData
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStateLiveData
import com.example.sergiobelda.iot_cloud_weather.livedata.WeatherStatesListLiveData
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreWeatherRepository : WeatherRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val collectionPath = "device-configs"

    override fun getDevices(): DevicesListLiveData {
        val ref = firestore.collection(collectionPath)
        return DevicesListLiveData(ref)
    }

    override fun getWeatherState(deviceId: String): WeatherStateLiveData {
        val ref = firestore.collection(collectionPath).document(deviceId)
        return WeatherStateLiveData(ref)
    }

    override fun getWeatherStates(deviceId: String): WeatherStatesListLiveData {
        val ref = firestore.collection(collectionPath).document(deviceId)
        return WeatherStatesListLiveData(ref)
    }
}
