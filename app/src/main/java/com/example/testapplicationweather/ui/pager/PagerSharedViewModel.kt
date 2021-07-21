package com.example.testapplicationweather.ui.pager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplicationweather.data.model.DailyModel

class PagerSharedViewModel : ViewModel() {
    private val _listOfDays = MutableLiveData<DailyModel>()
    val listOfDays:LiveData<DailyModel> get() = _listOfDays
    fun setValue(days: DailyModel) {
        _listOfDays.value = days
    }
}