package com.example.testapplicationweather.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.testapplicationweather.R
import com.example.testapplicationweather.databinding.FragmentMainBinding
import com.example.testapplicationweather.main.model.MainRepository
import com.example.testapplicationweather.main.pager.PagerSharedViewModel
import com.example.testapplicationweather.main.viewmodel.MainViewModel
import com.example.testapplicationweather.main.viewmodel.MainViewModelFactory
import com.example.testapplicationweather.utilites.*
import com.example.testapplicationweather.utilites.Resources.setIconsAndTitles
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewPager: ViewPager2

    private val repository = MainRepository()
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }
    private val sharedViewModel: PagerSharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setIconsAndTitles()
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
        viewPager = binding.mainViewPager
        viewPager.adapter = MainAdapter(this)

        val mapOfCity = mapOf(0 to getString(R.string.msk_name), 1 to getString(R.string.spb_name))

        TabLayoutMediator(binding.mainTabLayout, binding.mainViewPager) { tab, position ->
            tab.text = mapOfCity[position]
            binding.mainViewPager.setCurrentItem(tab.position, true)
        }.attach()

        setChangePagerListener()
    }

    private fun setChangePagerListener() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        mainViewModel.initRetrofit(GET_MSK)
                    }
                    1 -> {
                        mainViewModel.initRetrofit(GET_SPB)
                    }
                }
            }
        })
    }

    private fun initHead() {
        mainViewModel.weather.observe(this.requireActivity()) {
            val model = it.currently
            binding.mainFragmentTemperature.text = model.temperature.convertToCelsius()
            binding.mainFragmentWeather.text = getWeatherTitle(model.summary)
            binding.mainFragmentIcon.setIcon(model.icon)
            val listOfDays = it.daily
            sharedViewModel.listOfDays.value = listOfDays
        }
    }
}

