package com.example.testapplicationweather.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.testapplicationweather.R
import com.example.testapplicationweather.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.collections.Map as Map

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
        val msk = getString(R.string.msk_name)
        val spb = getString(R.string.spb_name)
        val mapOfCity = mapOf(0 to msk, 1 to spb)

        val adapter = MainAdapter(this)
        val viewPager = binding.mainViewPager

        viewPager.adapter = adapter
        TabLayoutMediator(binding.mainTabLayout, binding.mainViewPager) { tab, position ->
            tab.text = mapOfCity[position]
            binding.mainViewPager.setCurrentItem(tab.position, true)
        }.attach()

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0 -> {
                        Log.i("Pager:selected", "Selected moscow")
                    }
                    1 -> {
                        Log.i("Pager:selected", "Selected spb")
                    }
                }
            }
        })


    }
}