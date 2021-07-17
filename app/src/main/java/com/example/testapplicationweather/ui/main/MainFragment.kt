package com.example.testapplicationweather.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.testapplicationweather.R
import com.example.testapplicationweather.data.DataSource
import com.example.testapplicationweather.data.model.CurrentlyModel
import com.example.testapplicationweather.data.repository.MainRepository
import com.example.testapplicationweather.databinding.FragmentMainBinding
import com.example.testapplicationweather.ui.pager.PagerSharedViewModel
import com.example.testapplicationweather.utilites.*
import com.example.testapplicationweather.utilites.Resources.internetConnection
import com.example.testapplicationweather.ui.viewmodel.MainViewModel
import com.example.testapplicationweather.ui.viewmodel.MainViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2

    private val ioDispatcher = Dispatchers.IO
    private val dataSource = DataSource(ioDispatcher)
    private val repository = MainRepository(dataSource, ioDispatcher)
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
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.mainHeadLayoutInclude.headerProgressBar.visibility = View.VISIBLE
        initDataUpdate()
        initPager()
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
                        GET_MSK.getWeather()
                    }
                    1 -> {
                        GET_SPB.getWeather()
                    }
                }
            }
        })
    }

    private fun String.getWeather() {
        internetConnection = isNetworkConnected()
        mainViewModel.getWeatherFromRetrofit(this, true)
    }

    private fun initDataUpdate() {
        mainViewModel.weather.observe(viewLifecycleOwner) {
            it?.let {
                it.currently.bindUI()
                sharedViewModel.listOfDays.value = it.daily
            }
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

    private fun CurrentlyModel.bindUI() {
        binding.mainHeadLayoutInclude.headerProgressBar.visibility = View.GONE
        binding.mainHeadLayoutInclude.mainFragmentTemperature.text =
            this.temperature.convertToCelsius()
        binding.mainHeadLayoutInclude.mainFragmentWeather.text =
            getWeatherTitle(this.summary)
        binding.mainHeadLayoutInclude.mainFragmentIcon.setIcon(this.icon)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}



