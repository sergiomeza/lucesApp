package com.lucesapp

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector
import com.lucesapp.utils.Constants

sealed class Screen(val route: String,
                    @StringRes val resourceId: Int,
                    val icon: ImageVector) {
    object Sales : Screen(Constants.SALES_SCREEN,
        R.string.button_sales, Icons.Filled.ShoppingBag)
    object Products : Screen(Constants.PRODUCTS_SCREEN,
        R.string.button_products, Icons.Filled.List)
}