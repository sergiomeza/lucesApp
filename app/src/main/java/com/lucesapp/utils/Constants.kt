package com.lucesapp.utils

object Constants {
    const val SPLASH_ROUTE = "SplashScreen"
    const val HOME_ROUTE = "HomeScreen"
    const val SALES_SCREEN = "SalesScreen"
    const val PRODUCTS_SCREEN = "ProductsScreen"
}

inline fun <reified T: Any> Any.cast(): T{
    return this as T
}