package com.example.testapplicationweather.main

import android.util.Log
import androidx.core.os.persistableBundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testapplicationweather.main.model.DayModel
import com.example.testapplicationweather.main.pager.PagerFragment

class MainAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var list = mutableListOf<DayModel>()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = PagerFragment(list)

    fun setList(item: List<DayModel>) {
        list = item as MutableList<DayModel>
        Log.i("WEATHER:SET", list.toString())
    }

}