package com.example.testapplicationweather.main.pager

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplicationweather.databinding.ItemRecyclerViewBinding
import com.example.testapplicationweather.main.model.DayModel
import com.example.testapplicationweather.utilites.convertToCelsius
import com.example.testapplicationweather.utilites.convertToTime
import com.example.testapplicationweather.utilites.setIcon

class PagerViewHolder(private val binding: ItemRecyclerViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(day: DayModel, fragment: Fragment) {
        val temperatureHigh = day.temperatureHigh.toFloat()
        val temperatureLow = day.temperatureLow.toFloat()
        val temperature = ((temperatureHigh + temperatureLow) / 2).toString()
        fragment.setIcon(binding.itemRecyclerIcon, day.icon)
        binding.itemRecyclerDate.text = day.time.convertToTime()
        binding.itemRecyclerTemperature.text = temperature.convertToCelsius()
    }
}

