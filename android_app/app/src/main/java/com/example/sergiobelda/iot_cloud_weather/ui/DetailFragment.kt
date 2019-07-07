package com.example.sergiobelda.iot_cloud_weather.ui

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sergiobelda.iot_cloud_weather.R
import com.example.sergiobelda.iot_cloud_weather.viewmodel.WeatherStateViewModel
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_detail.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailFragment : Fragment() {
    private lateinit var viewModel: WeatherStateViewModel
    private lateinit var deviceId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deviceId = arguments!!.getString("device_id")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WeatherStateViewModel::class.java)

        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.setMaxX(20.0)
        setWeatherState()
        setWeatherPlots()
    }

    private fun setWeatherPlots() {
        if (deviceId != null){
            viewModel.getWeatherStates(deviceId).observe(this, Observer {weatherStates ->
                if (weatherStates != null){
                    val arrayDataPoint = Array(20) { DataPoint(0.0, 0.0) }
                    for (i in 0 until weatherStates.size) {
                        val json = JSONObject(weatherStates[i] as HashMap<*,*>)
                        val state : JSONObject = json.get("state") as JSONObject
                        arrayDataPoint[i] = DataPoint(i.toDouble(), state.get("temperature").toString().toDouble())
                    }
                    val series = LineGraphSeries<DataPoint>(arrayDataPoint)
                    series.color = ContextCompat.getColor(context!!, R.color.colorPrimaryDark)
                    graph.removeAllSeries()
                    graph.addSeries(series)
                } else {
                    Log.e("error:", "error")
                }
            })
        }
    }

    private fun setWeatherState() {
        if (deviceId != null){
            viewModel.getWeatherState(deviceId).observe(this, Observer { weatherState ->
                if (weatherState != null) {
                    val online = if (weatherState.online) "Connected" else "Disconnected"
                    val color = if (weatherState.online) R.color.colorOk else R.color.colorError
                    val spannable = SpannableString("${online} - Last update: ${weatherState.lastConnection}")
                    spannable.setSpan(
                        ForegroundColorSpan(ContextCompat.getColor(context!!, color)),
                        0, online.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    temp.text = weatherState.getTemperatureString()
                    humidity.text = weatherState.getHumidityString()
                    connectionDetails.text = spannable
                } else {
                    Log.e("error:", "error")
                }
            })
        }
    }
}
