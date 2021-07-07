package com.example.testapplicationweather.utilites

import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.testapplicationweather.R

object Resources {

    var iconClear: Drawable? = null
    var iconCloud: Drawable? = null
    var titleClear: String = ""
    var titleCloud: String = ""

    fun Fragment.setIconsAndTitles(){
        val theme = this.activity?.theme
        iconClear = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_wb_sunny_24, theme)
        iconCloud = ResourcesCompat.getDrawable(resources, R.drawable.ic_outline_wb_cloudy_24, theme)
        titleClear = getString(R.string.title_clear)
        titleCloud = getString(R.string.title_cloud)
    }

}