package com.example.sergiobelda.iot_cloud_weather.livedata

import android.text.format.DateFormat
import androidx.lifecycle.LiveData
import com.example.sergiobelda.iot_cloud_weather.model.WeatherState
import com.google.firebase.Timestamp
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
        try {
            if (snapshot != null && snapshot.exists()) {
                val state = snapshot.get("state") as HashMap<String, Number>
                val timestamp = snapshot.get("timestamp") as Timestamp
                val dateFormat = DateFormat.format("dd-MM-yyyy HH:mm:ss", timestamp.toDate()).toString()
                val online = snapshot.get("online") as Boolean
                val weatherState = WeatherState(
                    temperature = state["temperature"]!!.toDouble(),
                    humidity = state["humidity"]!!.toDouble(),
                    lastConnection = dateFormat,
                    online = online
                )
                value = weatherState
            } else if (exception != null) {

            }
        } catch (e : Exception){
            value = null
        }
    }
}
