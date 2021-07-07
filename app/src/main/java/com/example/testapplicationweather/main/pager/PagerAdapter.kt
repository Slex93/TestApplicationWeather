package com.example.testapplicationweather.main.pager

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplicationweather.databinding.ItemRecyclerViewBinding
import com.example.testapplicationweather.main.model.DayModel

class PagerAdapter(private val fragment: Fragment) : RecyclerView.Adapter<PagerViewHolder>() {

    private var list = mutableListOf<DayModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecyclerViewBinding.inflate(inflater, parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(list[position], fragment)
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: MutableList<DayModel>) {
        this.list = list
        notifyDataSetChanged()
    }

}