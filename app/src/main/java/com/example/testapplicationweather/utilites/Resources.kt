package com.example.testapplicationweather.utilites

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.testapplicationweather.R
import java.io.File

object Resources {
    var iconClear: Drawable? = null
    var iconCloud: Drawable? = null
    var stringClear: String = ""
    var stringCloud: String = ""
    var titleClear: String = ""
    var titleCloud: String = ""

    var cacheDirectory: File? = null
    var internetConnection:Boolean = false

    fun Fragment.setIconsAndTitles() {
        val theme = this.activity?.theme
        iconClear =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_wb_sunny_24, theme)
        iconCloud =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_outline_wb_cloudy_24, theme)
        titleClear = getString(R.string.title_clear)
        titleCloud = getString(R.string.title_cloud)
        stringClear = getString(R.string.string_clear)
        stringCloud = getString(R.string.string_cloud)
        cacheDirectory = requireContext().cacheDir
    }

}
