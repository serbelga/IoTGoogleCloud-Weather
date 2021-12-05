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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sergiobelda.iot_cloud_weather.R
import com.example.sergiobelda.iot_cloud_weather.databinding.FragmentDetailBinding
import com.example.sergiobelda.iot_cloud_weather.viewmodel.WeatherStateViewModel
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this)[WeatherStateViewModel::class.java] }

    private lateinit var deviceId: String

    private lateinit var binding: FragmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deviceId = arguments!!.getString("device_id")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.graph.viewport.isXAxisBoundsManual = true
        binding.graph.viewport.setMaxX(20.0)
        setWeatherState()
        setWeatherPlots()
    }

    private fun setWeatherPlots() {
        viewModel.getWeatherStates(deviceId).observe(this) { weatherStates ->
            if (weatherStates != null) {
                val arrayDataPoint = Array(20) { DataPoint(0.0, 0.0) }
                for (i in weatherStates.indices) {
                    val json = JSONObject(weatherStates[i] as HashMap<*, *>)
                    val state: JSONObject = json.get("state") as JSONObject
                    arrayDataPoint[i] = DataPoint(i.toDouble(), state.get("temperature").toString().toDouble())
                }
                val series = LineGraphSeries(arrayDataPoint)
                series.color = ContextCompat.getColor(context!!, R.color.colorPrimaryDark)
                binding.graph.removeAllSeries()
                binding.graph.addSeries(series)
            } else {
                Log.e(TAG, "Weather states null")
            }
        }
    }

    private fun setWeatherState() {
        viewModel.getWeatherState(deviceId).observe(this) { weatherState ->
            if (weatherState != null) {
                val online = if (weatherState.online) "Connected" else "Disconnected"
                val color = if (weatherState.online) R.color.colorOk else R.color.colorError
                val spannable = SpannableString("$online - Last update: ${weatherState.lastConnection}")
                spannable.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context!!, color)),
                    0, online.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                binding.weatherstate = weatherState
                binding.connectionDetails.text = spannable
            } else {
                Log.e(TAG, "Weather state null")
            }
        }
    }

    companion object {
        const val TAG = "DetailFragment"
    }
}
