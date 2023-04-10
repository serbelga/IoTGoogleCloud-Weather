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

package dev.sergiobelda.iot.cloud.weather.firestoredatasource

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dev.sergiobelda.iot.cloud.weather.data.Result
import dev.sergiobelda.iot.cloud.weather.firestoredatasource.FirestoreConstants.collectionPath
import dev.sergiobelda.iot.cloud.weather.firestoredatasource.mapper.DeviceWeatherStateMapper.map
import dev.sergiobelda.iot.cloud.weather.firestoredatasource.model.DeviceWeatherStateFirestore
import dev.sergiobelda.iot.cloud.weather.model.Device
import dev.sergiobelda.iot.cloud.weather.model.DeviceWeatherState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreDataSource : IFirestoreDataSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getDevices(): Flow<Result<List<Device>>> = callbackFlow {
        val collectionReference = Firebase.firestore.collection(collectionPath)
        val listenerRegistration = collectionReference.addSnapshotListener { value, error ->
            if (value != null) {
                val devices = mutableListOf<Device>()
                for (document in value.documents) {
                    devices.add(Device(document.id))
                }
                trySend(Result.Success(devices)).isSuccess
            } else {
                trySend(Result.Failure(exception = error)).isFailure
            }
        }
        awaitClose { listenerRegistration.remove() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getDeviceWeatherState(deviceId: String): Flow<Result<DeviceWeatherState>> =
        callbackFlow {
            val documentReference = Firebase.firestore.collection(collectionPath).document(deviceId)
            val listenerRegistration = documentReference.addSnapshotListener { value, error ->
                if (value != null) {
                    val deviceWeatherState =
                        value.toObject<DeviceWeatherStateFirestore>()?.map()

                    deviceWeatherState?.let {
                        trySend(Result.Success(it)).isSuccess
                    } ?: trySend(Result.Failure()).isFailure
                } else {
                    trySend(Result.Failure(exception = error)).isFailure
                }
            }
            awaitClose { listenerRegistration.remove() }
        }
}
