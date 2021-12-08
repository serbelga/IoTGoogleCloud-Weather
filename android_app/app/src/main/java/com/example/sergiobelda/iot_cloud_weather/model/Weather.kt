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

package com.example.sergiobelda.iot_cloud_weather.model

import com.example.sergiobelda.iot_cloud_weather.util.FormatNumber.getFormatNumberString
import java.io.Serializable

data class Weather(
    var temperature: Double?,
    var humidity: Double?
) : Serializable {
    fun getTemperatureString(): String =
        temperature?.let {
            "${getFormatNumberString(it)}°"
        } ?: "-°"

    fun getHumidityString(): String =
        humidity?.let {
            "${getFormatNumberString(it)}%"
        } ?: "-%"
}
