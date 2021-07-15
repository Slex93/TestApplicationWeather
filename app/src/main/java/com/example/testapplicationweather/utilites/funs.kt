package com.example.testapplicationweather.utilites

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.testapplicationweather.R
import com.example.testapplicationweather.utilites.Resources.iconClear
import com.example.testapplicationweather.utilites.Resources.iconCloud
import com.example.testapplicationweather.utilites.Resources.stringClear
import com.example.testapplicationweather.utilites.Resources.titleClear
import com.example.testapplicationweather.utilites.Resources.titleCloud
import com.google.android.gms.maps.model.LatLng
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
    val icon: Drawable? = if (name.contains(stringClear)) {
        iconClear
    } else iconCloud
    this.setImageDrawable(icon)
}

fun getWeatherTitle(primaryName: String): String =
    if (primaryName.lowercase().contains(stringClear)) {
        titleClear
    } else titleCloud

fun LatLng.getCoordinates(): String {
    val dec = DecimalFormat("#0.00000")
    val lat = dec.format(latitude).toString()
    val long = dec.format(longitude).toString()
    return "$lat, $long"
}

fun Fragment.setIconsAndTitles() {
    val theme = this.activity?.theme
    iconClear =
        ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_wb_sunny_24, theme)
    iconCloud =
        ResourcesCompat.getDrawable(resources, R.drawable.ic_outline_wb_cloudy_24, theme)
    titleClear = getString(R.string.title_clear)
    titleCloud = getString(R.string.title_cloud)
    stringClear = getString(R.string.string_clear)
    Resources.stringCloud = getString(R.string.string_cloud)
    Resources.cacheDirectory = requireContext().cacheDir
}