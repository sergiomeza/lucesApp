package com.lucesapp.utils

import android.annotation.SuppressLint
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val SPLASH_ROUTE = "SplashScreen"
    const val HOME_ROUTE = "HomeScreen"
    const val SALES_SCREEN = "SalesScreen"
    const val PRODUCTS_SCREEN = "ProductsScreen"
}

@SuppressLint("SimpleDateFormat")
fun Date.datetime(): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm")
    return formatter.format(this)
}

fun Long.price(): String {
    return "$${NumberFormat.getNumberInstance(Locale.US).format(this)}"
}