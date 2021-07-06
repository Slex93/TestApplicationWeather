package com.example.testapplicationweather.main.pager

import androidx.recyclerview.widget.RecyclerView
import com.example.testapplicationweather.databinding.ItemRecyclerViewBinding
import com.example.testapplicationweather.main.model.DayModel

class PagerViewHolder(private val binding: ItemRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(day: DayModel){
        binding.itemRecyclerDate.text = day.time.toString()
        binding.itemRecyclerTemperature.text = day.temperatureHigh.toString()
    }
}