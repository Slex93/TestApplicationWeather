package com.example.testapplicationweather.main.pager

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplicationweather.databinding.ItemRecyclerViewBinding
import com.example.testapplicationweather.main.model.DayModel

class PagerAdapter : RecyclerView.Adapter<PagerViewHolder>() {

    private var list = mutableListOf<DayModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerViewBinding.inflate(inflater, parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: MutableList<DayModel>) {
        Log.i("WEATHER:PAGER_A", list.toString())
        this.list = list
        notifyDataSetChanged()
    }

}