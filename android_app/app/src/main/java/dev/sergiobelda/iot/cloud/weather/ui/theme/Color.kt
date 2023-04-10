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

package dev.sergiobelda.iot.cloud.weather.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val primary = Color(0xFFEC4539)
val primaryLight = Color(0xFFFF7964)
val primaryDark = Color(0xFFB20010)

val secondary = Color(0xFFEC9727)
val secondaryLight = Color(0xFFFFC85A)
val secondaryDark = Color(0xFFB46900)

val red300 = Color(0xFFE57373)
val red800 = Color(0xFFC62828)

val teal200 = Color(0xFF80CBC4)
val teal400 = Color(0xFF26A69A)

val lightColors = lightColors(
    primary = primary,
    secondary = secondary,
)

val darkColors = darkColors(
    primary = primaryLight,
    onPrimary = Color.White,
    secondary = secondaryLight,
)

val Colors.offline: Color
    @Composable get() = if (isLight) red800 else red300

val Colors.online: Color
    @Composable get() = if (isLight) teal400 else teal200
