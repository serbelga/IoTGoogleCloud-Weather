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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sergiobelda.iot.cloud.weather.data.Result
import dev.sergiobelda.iot.cloud.weather.model.Device
import dev.sergiobelda.iot.cloud.weather.repository.IWeatherRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    weatherRepository: IWeatherRepository,
) : ViewModel() {

    val devices: StateFlow<Result<List<Device>>> =
        weatherRepository.getDevices().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Result.Loading,
        )
}
