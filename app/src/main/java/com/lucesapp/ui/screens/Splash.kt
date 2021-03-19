package com.lucesapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.lucesapp.R
import com.lucesapp.ui.theme.LucesTheme
import com.lucesapp.ui.theme.backgroundSplash
import com.lucesapp.ui.theme.logoColorSplash

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    LucesTheme {
        val tint = logoColorSplash
        Box(
            modifier =
            modifier
                .fillMaxSize()
                .background(backgroundSplash)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(painter = painterResource(id = R.drawable.ic_logo_luces),
                    contentDescription = "Luces verdes",
                    colorFilter = ColorFilter.tint(tint))
            }
        }
    }
}

@Preview
@Composable
fun SplashPreview() {
    SplashScreen()
}