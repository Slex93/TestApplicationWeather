package com.example.testapplicationweather.ui.pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplicationweather.data.model.DayModel
import com.example.testapplicationweather.databinding.ItemRecyclerViewBinding

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
        this.list = list
        notifyDataSetChanged()
    }

}