package com.example.sergiobelda.iot_cloud_weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sergiobelda.iot_cloud_weather.R
import com.example.sergiobelda.iot_cloud_weather.model.Device
import kotlinx.android.synthetic.main.device_item.view.*

class DevicesAdapter(private val devices: List<Device>, private val listener: (Device) -> Unit) : RecyclerView.Adapter<DevicesAdapter.DevicesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.device_item, parent, false)
        return DevicesViewHolder(itemView)
    }

    override fun getItemCount() = devices.size

    override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) = holder.bindItem(devices[position], listener)

    class DevicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(device: Device, listener: (Device) -> Unit) = with(itemView) {
            itemId.text = device.id
            setOnClickListener { listener(device) }
        }
    }
}