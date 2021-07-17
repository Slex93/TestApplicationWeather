package com.example.testapplicationweather.utilites

import androidx.fragment.app.Fragment
import com.example.testapplicationweather.data.DataSource
import com.example.testapplicationweather.data.repository.MainRepository
import com.example.testapplicationweather.ui.viewmodel.MainViewModelFactory
import kotlinx.coroutines.Dispatchers

open class BaseFragment: Fragment() {
    private val ioDispatcher = Dispatchers.IO
    private val dataSource = DataSource(ioDispatcher)
    private val repository = MainRepository(dataSource, ioDispatcher)
    open val viewModelFactory = MainViewModelFactory(repository)
}