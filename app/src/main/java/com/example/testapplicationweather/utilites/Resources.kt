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

}
