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

package dev.sergiobelda.iot.cloud.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sergiobelda.iot.cloud.weather.data.Result
import dev.sergiobelda.iot.cloud.weather.model.DeviceWeatherState
import dev.sergiobelda.iot.cloud.weather.repository.IWeatherRepository
import javax.inject.Inject

@HiltViewModel
class DeviceDetailViewModel @Inject constructor(
    private val weatherRepository: IWeatherRepository,
) : ViewModel() {

    fun getDeviceWeatherState(deviceId: String): LiveData<Result<DeviceWeatherState>> =
        weatherRepository.getDeviceWeatherState(deviceId).asLiveData()
}
