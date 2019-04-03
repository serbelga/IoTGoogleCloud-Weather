package com.example.sergiobelda.iot_cloud_weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.sergiobelda.iot_cloud_weather.model.WeatherState
import com.example.sergiobelda.iot_cloud_weather.viewmodel.WeatherStateViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(WeatherStateViewModel::class.java)
        val liveData = viewModel.getWeatherStateLiveData("esp8266_4CB5CD")
        liveData.observe(this, Observer<WeatherState> { weatherState ->
            if (weatherState != null) {
                val temperature = weatherState.temperature + '°'
                temp.text = temperature
            }
        })
    }
}
