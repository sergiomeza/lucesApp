package com.lucesapp.ui.screens

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.google.gson.Gson
import com.lucesapp.R
import com.lucesapp.model.Product
import com.lucesapp.ui.theme.LucesTheme
import com.lucesapp.ui.views.ProductForm
import com.lucesapp.utils.cast
import com.lucesapp.viewmodel.FirestoreViewModel
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@ExperimentalAnimationApi
@Composable
fun ProductFormScreen(
    firestoreViewModel: FirestoreViewModel,
    onBackPressed: () -> Unit
) {
    LucesTheme {
        ProvideWindowInsets {
            Scaffold(
                topBar = {
                    FormTopAppBar(
                        topAppBarText = stringResource(id = R.string.product_create),
                        onBackPressed = {
                            onBackPressed()
                        }
                    )
                },
                content = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        val loading = firestoreViewModel.showLoading.observeAsState()
                        val dataAdded = firestoreViewModel.dataAdded.observeAsState()
                        dataAdded.value?.let {
                            if (it.isNotEmpty()){
                                firestoreViewModel.clearDataAdded()
                                onBackPressed()
                            }
                        }
                        ProductForm(onFormSubmitted = { name, description, price, retail ->
                            val product = Product(
                                name = name, description = description,
                                price = price.toLong(),
                                retailPrice = retail.toLong())
                            firestoreViewModel.addProduct(product = product)
                        }, isLoading = loading)
                    }
                }
            )
        }
    }
}

@Composable
fun FormTopAppBar(topAppBarText: String, onBackPressed: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = topAppBarText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        },
        // We need to balance the navigation icon, so we add a spacer.
        actions = {
            Spacer(modifier = Modifier.width(68.dp))
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
        modifier = Modifier.statusBarsPadding()
    )
}