package com.example.testapplicationweather.utilites

import java.text.DecimalFormat

fun String.refactorToCelsius(): String {
    val temp = (this.toFloat() - 32) * 5 / 9
    val dec = DecimalFormat("#0.0")
    val result = dec.format(temp).toString() + " \u2103"
    return (result)
}