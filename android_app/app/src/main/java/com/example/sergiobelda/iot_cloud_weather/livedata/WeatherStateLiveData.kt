package com.example.sergiobelda.iot_cloud_weather.livedata

import androidx.lifecycle.LiveData
import com.example.sergiobelda.iot_cloud_weather.model.WeatherState
import com.google.firebase.firestore.*

class WeatherStateLiveData(private val documentReference: DocumentReference) : LiveData<WeatherState>(), EventListener<DocumentSnapshot> {
    private var listenerRegistration: ListenerRegistration? = null

    override fun onActive() {
        listenerRegistration = documentReference.addSnapshotListener(this)
    }

    override fun onInactive() {
        listenerRegistration!!.remove()
    }

    override fun onEvent(snapshot: DocumentSnapshot?, exception: FirebaseFirestoreException?) {
        if (snapshot != null && snapshot.exists()) {
            val state = snapshot.get("state") as HashMap<*, *>
            val weatherState = WeatherState(
                temperature = state["t"].toString(),
                humidity = state["h"].toString()
            )
            value = weatherState
        } else if (exception != null) {

        }
    }
}
