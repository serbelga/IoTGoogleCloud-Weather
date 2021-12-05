package com.example.sergiobelda.iot_cloud_weather.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sergiobelda.iot_cloud_weather.databinding.DeviceItemBinding
import com.example.sergiobelda.iot_cloud_weather.model.Device

class DevicesAdapter(
    private val devices: List<Device>,
    private val listener: (Device) -> Unit
) : RecyclerView.Adapter<DevicesAdapter.DevicesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesViewHolder =
        DevicesViewHolder(
            DeviceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = devices.size

    override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) =
        holder.bindItem(devices[position], listener)

    class DevicesViewHolder(private val binding: DeviceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(device: Device, listener: (Device) -> Unit) = with(itemView) {
            binding.itemId.text = device.id
            setOnClickListener { listener(device) }
        }
    }
}
