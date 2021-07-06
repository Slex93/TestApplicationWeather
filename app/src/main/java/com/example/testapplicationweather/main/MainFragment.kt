package com.example.testapplicationweather.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapplicationweather.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        initPager()
        return binding.root
    }

    private fun initPager() {
        val mapOfCity = mutableMapOf<Int, String>()
        mapOfCity[0] = "Москва"
        mapOfCity[1] = "Санкт-Петербург"
        binding.mainViewPager.adapter = MainAdapter(this)
        TabLayoutMediator(binding.mainTabLayout, binding.mainViewPager) { tab, position ->
            tab.text = mapOfCity[position]
            binding.mainViewPager.setCurrentItem(tab.position, true)
        }.attach()
    }
}