package com.example.testapplicationweather.utilites

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.example.testapplicationweather.utilites.Resources.iconClear
import com.example.testapplicationweather.utilites.Resources.iconCloud
import com.example.testapplicationweather.utilites.Resources.titleClear
import com.example.testapplicationweather.utilites.Resources.titleCloud
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.convertToCelsius(): String {
    val temp = (this.toFloat() - 32) * 5 / 9
    val dec = DecimalFormat("#0.0")
    val result = dec.format(temp).toString() + " \u2103"
    return (result)
}

fun String.convertToTime(): String {
    val format = "EEE, d MMMM"
    val rawDate = this.toLong()
    val newDate = Date(rawDate * 1000L)
    val sdf = SimpleDateFormat(format, Locale("ru"))
    sdf.timeZone = TimeZone.getTimeZone("GMT-4")
    val day = sdf.format(newDate)
    return day
}

fun ImageView.setIcon(name: String) {
    val icon: Drawable? = if (name.contains("clear")) {
        iconClear
    } else iconCloud
    this.setImageDrawable(icon)
}

fun getWeatherTitle(primaryName: String): String =
    if (primaryName.contains("Clear")){
        titleClear
    } else titleCloud
