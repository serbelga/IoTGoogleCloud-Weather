package com.example.sergiobelda.iot_cloud_weather.ui


import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.sergiobelda.iot_cloud_weather.R
import com.example.sergiobelda.iot_cloud_weather.viewmodel.WeatherStateViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


/**
 * A simple [Fragment] subclass.
 *
 */
class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = "esp8266_4CB5CD"
        deviceId.text = "Device ID: $id"
        val viewModel = ViewModelProviders.of(this).get(WeatherStateViewModel::class.java)

        val liveData = viewModel.getWeatherStateLiveData("esp8266_4CB5CD")
        liveData.observe(this, Observer { weatherState ->
            if (weatherState != null) {
                val online = if (weatherState.online) "Connected" else "Disconnected"
                val color = if (weatherState.online) R.color.teal else R.color.red
                val spannable = SpannableString("${online} - Last update: ${weatherState.lastConnection}")
                spannable.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context!!, color)),
                    0, online.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                temp.text = weatherState.getTemperatureString()
                humidity.text = weatherState.getHumidityString()
                connectionDetails.text = spannable
            }
        })
    }
}
