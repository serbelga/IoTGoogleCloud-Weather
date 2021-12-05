package com.example.sergiobelda.iot_cloud_weather.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sergiobelda.iot_cloud_weather.R
import com.example.sergiobelda.iot_cloud_weather.databinding.ActivityMainBinding
import com.example.sergiobelda.iot_cloud_weather.viewmodel.DevicesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var expanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar to handle Backdrop events and Switch Theme
        setupToolbar()

        val viewModel = ViewModelProvider(this)[DevicesViewModel::class.java]
        viewModel.getDevices().observe(this) { devices ->
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = DevicesAdapter(devices) { device ->
                val bundle = Bundle()
                bundle.putString("device_id", device.id)
                val detailFragment = DetailFragment()
                detailFragment.arguments = bundle
                supportFragmentManager.commit {
                    replace(R.id.backdrop, detailFragment)
                }
                binding.deviceButton.text = device.id
                setToolbarExpanded(expanded)
                expanded = !expanded
            }
        }
    }

    private fun setupToolbar() {
        val themeMenuItem = binding.toolbar.menu.findItem(R.id.theme)
        themeMenuItem.setOnMenuItemClickListener {
            when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_NO -> setDefaultNightMode(MODE_NIGHT_YES)
                Configuration.UI_MODE_NIGHT_YES -> setDefaultNightMode(MODE_NIGHT_NO)
            }
            true
        }

        binding.deviceButton.setOnClickListener {
            setToolbarExpanded(expanded)
            expanded = !expanded
        }
    }

    private fun setToolbarExpanded(expanded: Boolean) {
        if (expanded) {
            binding.motionLayout.transitionToStart()
            binding.deviceButton.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_arrow_drop_down_black_24dp)
        } else {
            binding.motionLayout.transitionToEnd()
            binding.deviceButton.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_arrow_drop_up_black_24dp)
        }
    }
}
