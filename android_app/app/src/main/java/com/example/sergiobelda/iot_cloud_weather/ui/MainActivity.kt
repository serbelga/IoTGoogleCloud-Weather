package com.example.sergiobelda.iot_cloud_weather.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sergiobelda.iot_cloud_weather.R
import com.example.sergiobelda.iot_cloud_weather.adapter.DevicesAdapter
import com.example.sergiobelda.iot_cloud_weather.viewmodel.DevicesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var expanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Setup Toolbar to handle Backdrop events and Switch Theme
        setupToolbar()


        val viewModel = ViewModelProvider(this).get(DevicesViewModel::class.java)
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
                    setToolbarExpanded(expanded)
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
            setToolbarExpanded(expanded)
            expanded = !expanded
        }
    }

    private fun setToolbarExpanded(expanded: Boolean){
        if (expanded) {
            motionLayout.transitionToStart()
            deviceButton.icon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_drop_down_black_24dp)
        } else {
            motionLayout.transitionToEnd()
            deviceButton.icon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_drop_up_black_24dp)
        }
    }
}
