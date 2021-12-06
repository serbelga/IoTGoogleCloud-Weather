/*
 * Copyright 2021 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sergiobelda.iot_cloud_weather.ui

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sergiobelda.iot_cloud_weather.R
import com.example.sergiobelda.iot_cloud_weather.data.doIfSuccess
import com.example.sergiobelda.iot_cloud_weather.databinding.DeviceDetailFragmentBinding
import com.example.sergiobelda.iot_cloud_weather.viewmodel.DeviceDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class DeviceDetailFragment : Fragment() {

    private val viewModel by viewModels<DeviceDetailViewModel>()

    private lateinit var deviceId: String

    private lateinit var binding: DeviceDetailFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deviceId = arguments!!.getString(ARG_DEVICE_ID)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.device_detail_fragment, container, false)
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
        /*
        viewModel.getDeviceLastWeatherList(deviceId).observe(this) { weatherStates ->
            if (weatherStates != null) {
                val arrayDataPoint = Array(20) { DataPoint(0.0, 0.0) }
                for (i in weatherStates.indices) {
                    val json = JSONObject(weatherStates[i] as HashMap<*, *>)
                    val state: JSONObject = json.get("state") as JSONObject
                    arrayDataPoint[i] =
                        DataPoint(i.toDouble(), state.get("temperature").toString().toDouble())
                }
                val series = LineGraphSeries(arrayDataPoint)
                series.color = ContextCompat.getColor(context!!, R.color.colorPrimaryDark)
                binding.graph.removeAllSeries()
                binding.graph.addSeries(series)
            } else {
                Log.e(TAG, "Weather states null")
            }
        }

         */
    }

    private fun setWeatherState() {
        viewModel.getDeviceWeatherState(deviceId).observe(this) { result ->
            result.doIfSuccess { value ->
                val online = if (value.online) "Connected" else "Disconnected"
                val color = if (value.online) R.color.colorOk else R.color.colorError
                val spannable =
                    SpannableString("$online - Last update: ${value.lastConnection}")
                spannable.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context!!, color)),
                    0, online.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.weather = value.weather
                binding.connectionDetails.text = spannable
            }
        }
    }

    companion object {
        private const val TAG = "DetailFragment"
        const val ARG_DEVICE_ID = "device_id"
    }
}
