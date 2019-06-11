package com.example.sergiobelda.iot_cloud_weather.ui

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sergiobelda.iot_cloud_weather.R
import com.example.sergiobelda.iot_cloud_weather.adapter.DevicesAdapter
import com.example.sergiobelda.iot_cloud_weather.transitions.NavigationIconClickListener
import com.example.sergiobelda.iot_cloud_weather.viewmodel.DevicesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //delegate.localNightMode = MODE_NIGHT_NO

        setContentView(R.layout.activity_main)

        // Setup Toolbar to handle Backdrop events and Switch Theme
        setupToolbar()

        val viewModel = ViewModelProviders.of(this).get(DevicesViewModel::class.java)
        viewModel.devices.observe(this, Observer { devices ->
            recyclerView.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?
            recyclerView.adapter = DevicesAdapter(devices) {
                    device -> Log.d("Id: ", device.id)
            }
        })

        supportFragmentManager.commit {
            add(R.id.backdrop, DetailFragment())
        }
    }

    private fun setupToolbar() {
        val themeMenuItem = toolbar.menu.findItem(R.id.theme)
        themeMenuItem.setOnMenuItemClickListener {

            when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_NO -> setDefaultNightMode(MODE_NIGHT_YES)
                Configuration.UI_MODE_NIGHT_YES -> setDefaultNightMode(MODE_NIGHT_NO)
            }
            true
        }
        toolbar.setNavigationOnClickListener(
            NavigationIconClickListener(
                this,
                backdrop,
                AccelerateDecelerateInterpolator(),
                R.drawable.ic_menu_black_24dp, // Menu open icon
                R.drawable.ic_close_black_24dp
            )
        ) // Menu close icon
    }
}
