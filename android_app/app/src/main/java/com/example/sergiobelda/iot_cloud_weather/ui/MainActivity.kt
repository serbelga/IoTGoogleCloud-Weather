package com.example.sergiobelda.iot_cloud_weather.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sergiobelda.iot_cloud_weather.R
import com.example.sergiobelda.iot_cloud_weather.adapter.DevicesAdapter
import com.example.sergiobelda.iot_cloud_weather.viewmodel.DevicesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var expanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //delegate.localNightMode = MODE_NIGHT_NO

        setContentView(R.layout.activity_main)

        // Setup Toolbar to handle Backdrop events and Switch Theme
        setupToolbar()


        val viewModel = ViewModelProviders.of(this).get(DevicesViewModel::class.java)
        viewModel.devices.observe(this, Observer { devices ->
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = DevicesAdapter(devices) {
                device ->
                    var bundle = Bundle()
                    bundle.putString("device_id", device.id)
                    var detailFragment = DetailFragment()
                    detailFragment.arguments = bundle
                    supportFragmentManager.commit {
                        replace(R.id.backdrop, detailFragment)
                    }
                    deviceButton.text = device.id
                    motionLayout.transitionToStart()
                    expanded = !expanded
            }
        })
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

        deviceButton.setOnClickListener {
            if (expanded) {
                motionLayout.transitionToStart()
            } else {
                motionLayout.transitionToEnd()
            }
            expanded = !expanded
        }
    }
}
