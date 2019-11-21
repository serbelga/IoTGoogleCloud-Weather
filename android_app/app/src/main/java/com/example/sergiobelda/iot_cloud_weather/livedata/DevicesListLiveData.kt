package com.example.sergiobelda.iot_cloud_weather.livedata

import androidx.lifecycle.LiveData
import com.example.sergiobelda.iot_cloud_weather.model.Device
import com.google.firebase.firestore.*

class DevicesListLiveData(private val collectionReference: CollectionReference) : LiveData<List<Device>>(), EventListener<QuerySnapshot> {
    private var listenerRegistration: ListenerRegistration? = null

    override fun onActive() {
        listenerRegistration = collectionReference.addSnapshotListener(this)
    }

    override fun onInactive() {
        listenerRegistration!!.remove()
    }

    override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
        val devices = ArrayList<Device>()
        if (snapshot != null) {
            for (document in snapshot.documents) {
                devices.add(Device(document.id))
            }
        } else if (exception != null) {

        }
        value = devices
    }
}