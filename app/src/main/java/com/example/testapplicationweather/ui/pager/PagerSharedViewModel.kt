package com.example.testapplicationweather.ui.pager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplicationweather.data.model.DailyModel

class PagerSharedViewModel : ViewModel() {
    val listOfDays = MutableLiveData<DailyModel>()
}