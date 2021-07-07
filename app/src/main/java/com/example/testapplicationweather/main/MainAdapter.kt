package com.example.testapplicationweather.main

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testapplicationweather.main.model.DailyModel
import com.example.testapplicationweather.main.model.DayModel
import com.example.testapplicationweather.main.pager.PagerFragment

class MainAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var list = MutableLiveData<DailyModel>()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = PagerFragment(list)

    fun setList(item: DailyModel) {
        list.value = item
        Log.i("WEATHER:SET", item.toString())
    }

}