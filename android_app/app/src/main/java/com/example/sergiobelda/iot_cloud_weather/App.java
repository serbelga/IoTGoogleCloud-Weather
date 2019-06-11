package com.example.sergiobelda.iot_cloud_weather;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;

public class App extends Application {

    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
}
