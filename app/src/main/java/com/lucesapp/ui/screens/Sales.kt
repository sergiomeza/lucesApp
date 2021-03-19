package com.lucesapp.ui.screens

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.transform.CircleCropTransformation
import com.lucesapp.R
import com.lucesapp.model.Product
import com.lucesapp.model.Sale
import com.lucesapp.ui.activities.ProductFormActivity
import com.lucesapp.ui.activities.SaleFormActivity
import com.lucesapp.ui.theme.LucesTheme
import com.lucesapp.ui.theme.typography
import com.lucesapp.utils.price
import com.lucesapp.viewmodel.FirestoreViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

@ExperimentalAnimationApi
@Composable
fun SalesScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    firestoreViewModel: FirestoreViewModel
) {
    LucesTheme {
        val context = LocalContext.current
        firestoreViewModel.getSales()
        val sales: List<Sale> by firestoreViewModel.sales.observeAsState(listOf())
        val loading = firestoreViewModel.showLoading.observeAsState()
        Scaffold(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        context.startActivity(Intent(context, SaleFormActivity::class.java))
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(bottom = 100.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            },
            floatingActionButtonPosition = FabPosition.End,
        ) {
            loading.value?.let {
                AnimatedVisibility(visible = it) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ){
                        CircularProgressIndicator()
                    }
                }
            }
            Box {
                val listState = rememberLazyListState()
                LazyColumn(
                    state = listState,
                    modifier = Modifier.padding(bottom = 110.dp)
                ) {
                    items(sales){ sale ->
                        SaleRow(sale = sale)
                    }
                }
            }
        }
    }
}

@Composable
fun SaleRow(sale: Sale){
    Card(
        shape = RoundedCornerShape(6.dp),
        elevation = 4.dp,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)

    ) {
        Box(
            modifier = Modifier.background(MaterialTheme.colors.surface)
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CoilImage(
                    data = sale.product?.image ?: "",
                    contentDescription = sale.product?.name,
                    contentScale = ContentScale.Fit,
                    requestBuilder = {
                        transformations(CircleCropTransformation())
                    },
                    fadeIn = true,
                    loading = {
                        Box(Modifier.matchParentSize()) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    },
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo_luces),
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .size(65.dp)
                        .padding(end = 16.dp, start = 8.dp)
                )
                Column {
                    Text(
                        text = "${sale.product?.name}",
                        style = MaterialTheme.typography.h3.copy(fontSize = 15.sp),
                    )
                    Text(
                        text = "${sale.created}",
                        style = typography.body2,
                    )
                }
                Spacer(Modifier.weight(1f))
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "${sale.quantity} x ${sale.product?.retailPrice?.price()}",
                        style = typography.body1.copy(fontSize = 10.sp),
                        modifier = Modifier
                            .padding(vertical = 2.dp),
                        textAlign = TextAlign.End
                    )
                    PriceTag(
                        textColor = MaterialTheme.colors.primary,
                        price = (sale.product?.retailPrice ?: 0) * (sale.quantity ?: 1),
                        textStyle = MaterialTheme.typography.h3.copy(fontSize = 16.sp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSaleRow(){
    val product = Product(
        id = "", name = "Jabon de avena", description = "",
        image = "https://firebasestorage.googleapis.com/v0/b/sergiomeza-e5bfa.appspot.com/o/LucesVerdes%2Fsoap_1.jpg?alt=media&token=a28af1c3-13ec-4aa6-b19f-80ab2606b5e3",
        price = 3050, retailPrice = 8000
    )
    val sale = Sale(product = product, created = "21/19/2444", quantity = 2)
    SaleRow(sale = sale)
}