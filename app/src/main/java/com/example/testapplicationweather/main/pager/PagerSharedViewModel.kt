package com.example.testapplicationweather.main.pager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplicationweather.main.model.DailyModel

class PagerSharedViewModel : ViewModel() {

    val listOfDays = MutableLiveData<DailyModel>()

}