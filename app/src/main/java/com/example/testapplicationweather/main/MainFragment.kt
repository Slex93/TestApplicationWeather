package com.example.testapplicationweather.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.testapplicationweather.R
import com.example.testapplicationweather.databinding.FragmentMainBinding
import com.example.testapplicationweather.main.model.MainRepository
import com.example.testapplicationweather.main.viewmodel.MainViewModel
import com.example.testapplicationweather.main.viewmodel.MainViewModelFactory
import com.example.testapplicationweather.utilites.refactorToCelsius
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: MainAdapter
    private lateinit var tab: TabLayout.Tab

    private val repository = MainRepository()
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        mainViewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
        initPager()
        initHead()
        return binding.root
    }

    private fun initPager() {
        val msk = getString(R.string.msk_name)
        val spb = getString(R.string.spb_name)
        val mapOfCity = mapOf(0 to msk, 1 to spb)

        adapter = MainAdapter(this)
        val viewPager = binding.mainViewPager

        viewPager.adapter = adapter
        TabLayoutMediator(binding.mainTabLayout, binding.mainViewPager) { tab, position ->
            this.tab = tab
            tab.text = mapOfCity[position]
            binding.mainViewPager.setCurrentItem(tab.position, true)
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        //mainViewModel.initRetrofit(GET_MSK)
                    }
                    1 -> {
                        //mainViewModel.initRetrofit(GET_SPB)
                    }
                }
            }
        })

    }

    private fun initHead() {
        mainViewModel.weather.observe(this.requireActivity()) {
            val model = it.currently
            binding.mainFragmentTemperature.text = model.temperature.refactorToCelsius()
            binding.mainFragmentWeather.text = model.summary

            val listOfDays = it.daily.data
            Log.i("WEATHER:INIT", (listOfDays).toString())

            adapter.setList(listOfDays)
        }
    }
}
