package com.example.sergiobelda.iot_cloud_weather.livedata

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*

class WeatherStatesLiveData(private val documentReference: DocumentReference) : LiveData<Array<*>>(), EventListener<DocumentSnapshot> {
    private var listenerRegistration: ListenerRegistration? = null

    override fun onActive() {
        listenerRegistration = documentReference.addSnapshotListener(this)
    }

    override fun onInactive() {
        listenerRegistration!!.remove()
    }

    override fun onEvent(snapshot: DocumentSnapshot?, exception: FirebaseFirestoreException?) {
        if (snapshot != null && snapshot.exists()) {
            val states = snapshot.get("states") as ArrayList<*>

            value = states.toArray()
        } else if (exception != null) {

        }
    }
}