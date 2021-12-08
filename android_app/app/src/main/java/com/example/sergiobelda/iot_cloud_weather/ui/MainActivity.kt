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
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sergiobelda.iot_cloud_weather.R
import com.example.sergiobelda.iot_cloud_weather.data.doIfSuccess
import com.example.sergiobelda.iot_cloud_weather.model.Device
import com.example.sergiobelda.iot_cloud_weather.ui.theme.IoTCloudWeatherTheme
import com.example.sergiobelda.iot_cloud_weather.viewmodel.DeviceDetailViewModel
import com.example.sergiobelda.iot_cloud_weather.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IoTCloudWeatherApp()
        }
    }
}

@Composable
fun IoTCloudWeatherApp() {
    IoTCloudWeatherTheme {
        MainScreen()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val devicesResult by mainViewModel.devices.collectAsState()
    var selected by remember { mutableStateOf(0) }
    val scaffoldState = rememberBackdropScaffoldState(
        BackdropValue.Concealed
    )
    val scope = rememberCoroutineScope()
    BackdropScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                navigationIcon = {
                    if (scaffoldState.isConcealed) {
                        IconButton(
                            onClick = {
                                scope.launch { scaffoldState.reveal() }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                scope.launch { scaffoldState.conceal() }
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                },
                elevation = 0.dp,
                backgroundColor = Color.Transparent
            )
        },
        backLayerContent = {
            devicesResult.doIfSuccess { devices ->
                DeviceList(devices, selected) {
                    selected = it
                }
            }
        },
        frontLayerContent = {
            devicesResult.doIfSuccess { list ->
                list.getOrNull(selected)?.id?.let { DeviceDetailView(it) }
            }
        }
    )
}

@Composable
fun DeviceList(devices: List<Device>, selected: Int, onClick: (Int) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            stringResource(id = R.string.devices).uppercase(),
            style = MaterialTheme.typography.overline
        )
        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            itemsIndexed(devices) { index, device ->
                DeviceItem(device = device, isSelected = index == selected) { onClick(index) }
            }
        }
    }
}

@Composable
fun DeviceItem(device: Device, isSelected: Boolean, onClick: () -> Unit) {
    val modifier = if (isSelected) {
        Modifier.background(
            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.2f),
            shape = MaterialTheme.shapes.medium
        )
    } else {
        Modifier
    }
    Row(
        modifier = modifier
            .height(36.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .selectable(isSelected, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            device.id ?: "-",
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun DeviceDetailView(deviceId: String, deviceDetailViewModel: DeviceDetailViewModel = viewModel()) {
    val deviceWeatherState by deviceDetailViewModel.getDeviceWeatherState(deviceId).observeAsState()
    deviceWeatherState?.doIfSuccess {
        Text(it.weather?.getTemperatureString().toString())
    }
}
