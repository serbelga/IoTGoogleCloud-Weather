package com.example.sergiobelda.iot_cloud_weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
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

        toolbar.setNavigationOnClickListener(NavigationIconClickListener(
            this,
            backdrop,
            AccelerateDecelerateInterpolator(),
            R.drawable.ic_menu_black_24dp, // Menu open icon
            R.drawable.ic_close_black_24dp)) // Menu close icon

        supportFragmentManager.beginTransaction().add(R.id.backdrop, DetailFragment()).commit()
    }
}
