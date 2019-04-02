package com.example.sergiobelda.iot_cloud_weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDatabase()
    }

    private fun initDatabase() {
        val firestore = FirebaseFirestore.getInstance()
        val ref = firestore.collection("device-configs").document("esp8266_4CB5CD")
        ref.addSnapshotListener { snapshot, exception ->
            if (snapshot != null) {
                val state = snapshot.get("state") as HashMap<*, *>
                val weatherState = WeatherState(
                    temperature = state["t"].toString(),
                    humidity = state["h"].toString()
                )
                temp.text = weatherState.temperature
            } else if (exception != null) {

            }
        }
    }
}
