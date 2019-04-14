package com.example.sergiobelda.iot_cloud_weather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sergiobelda.iot_cloud_weather.R
import com.example.sergiobelda.iot_cloud_weather.adapter.DevicesAdapter
import com.example.sergiobelda.iot_cloud_weather.model.Device
import com.example.sergiobelda.iot_cloud_weather.transitions.NavigationIconClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setNavigationOnClickListener(
            NavigationIconClickListener(
                this,
                backdrop,
                AccelerateDecelerateInterpolator(),
                R.drawable.ic_menu_black_24dp, // Menu open icon
                R.drawable.ic_close_black_24dp
            )
        ) // Menu close icon

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        var device = Device("esp8266_4CB5CD")
        var devices = ArrayList<Device>()
        devices.add(device)
        recyclerView.adapter = DevicesAdapter(devices) {
            Log.d("Id: ", it.id)
        }
        supportFragmentManager.beginTransaction().add(
            R.id.backdrop,
            DetailFragment()
        ).commit()
    }
}
