package com.lucesapp.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.core.view.WindowCompat
import com.lucesapp.ui.screens.HomeScreen
import com.lucesapp.viewmodel.FirestoreViewModel

class MainActivity : AppCompatActivity() {
    private val firestoreViewModel by viewModels<FirestoreViewModel>()

    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HomeScreen(firestoreViewModel = firestoreViewModel)
        }
    }
}