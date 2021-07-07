package com.example.testapplicationweather.utilites

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.testapplicationweather.R
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

fun Fragment.setIcon(iconView: ImageView, name: String) {
    val theme = this.activity?.theme
    val icon : Drawable? = if (name.contains("clear")){
        ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_wb_sunny_24, theme)
    } else {
        ResourcesCompat.getDrawable(resources, R.drawable.ic_outline_wb_cloudy_24, theme)
    }
    iconView.setImageDrawable(icon)
}

fun Fragment.getWeatherTitle(primaryName: String): String{
    when(primaryName){
        "Cloudy" -> return "Облачно"
        "Clear" -> return "Ясно"
        "Mostly Cloudy" -> return "Преимущественно облачно"
        else -> return "Ясно"
    }
}