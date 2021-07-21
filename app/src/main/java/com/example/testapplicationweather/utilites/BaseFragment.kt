package com.example.testapplicationweather.utilites

import androidx.fragment.app.Fragment
import com.example.testapplicationweather.data.DataSource
import com.example.testapplicationweather.data.repository.MainRepository
import com.example.testapplicationweather.ui.viewmodel.MainViewModelFactory

open class BaseFragment : Fragment() {
    private val dataSource = DataSource()
    private val repository = MainRepository(dataSource)
    open val viewModelFactory = MainViewModelFactory(repository)
}