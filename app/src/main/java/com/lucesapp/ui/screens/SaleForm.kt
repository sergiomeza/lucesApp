package com.lucesapp.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.lucesapp.R
import com.lucesapp.model.Sale
import com.lucesapp.ui.theme.LucesTheme
import com.lucesapp.ui.views.SaleForm
import com.lucesapp.utils.datetime
import com.lucesapp.viewmodel.FirestoreViewModel
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import java.util.*

@ExperimentalAnimationApi
@Composable
fun SaleFormScreen(
    firestoreViewModel: FirestoreViewModel,
    onBackPressed: () -> Unit
) {
    LucesTheme {
        ProvideWindowInsets {
            Scaffold(
                topBar = {
                    FormTopAppBar(
                        topAppBarText = stringResource(id = R.string.sale_create),
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
                        val products = firestoreViewModel.products.observeAsState()
                        firestoreViewModel.getProducts()
                        dataAdded.value?.let {
                            if (it.isNotEmpty()){
                                firestoreViewModel.clearDataAdded()
                                onBackPressed()
                            }
                        }
                        SaleForm(onFormSubmitted = { product, quantity ->
                            val sale = Sale(quantity = quantity, product = product,
                                created = Date().datetime()
                            )
                            firestoreViewModel.addData(sale)
                        }, isLoading = loading, products = products.value)
                    }
                }
            )
        }
    }
}