package com.example.sergiobelda.iot_cloud_weather.livedata

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration

class WeatherStatesListLiveData(private val documentReference: DocumentReference) :
    LiveData<Array<*>>(),
    EventListener<DocumentSnapshot> {
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
                val states = snapshot.get("states") as ArrayList<*>

                value = states.toArray()
            }
        } catch (e: Exception) { }
    }
}
