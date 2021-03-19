package com.lucesapp.ui.screens

import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lucesapp.model.Product
import com.lucesapp.ui.theme.LucesTheme
import com.lucesapp.viewmodel.FirestoreViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import java.util.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.lucesapp.R
import com.lucesapp.ui.activities.ProductFormActivity
import com.lucesapp.ui.theme.typography
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import java.text.NumberFormat

@ExperimentalAnimationApi
@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    firestoreViewModel: FirestoreViewModel
) {
    LucesTheme {
        val context = LocalContext.current
        firestoreViewModel.getProducts()
        val products: List<Product> by firestoreViewModel.products.observeAsState(listOf())
        val loading = firestoreViewModel.showLoading.observeAsState()
        Scaffold(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        context.startActivity(Intent(context, ProductFormActivity::class.java))
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
            Box(modifier = Modifier) {
                val listState = rememberLazyListState()
                LazyColumn(content = {
                    items(products){ product ->
                        ProductRow(product =  product, modifier = Modifier)
                    }
                }, state = listState,
                    modifier = Modifier.padding(bottom = 110.dp))
            }
        }
    }
}

@Composable
fun ProductRow(
    product: Product,
    modifier: Modifier) {
    Box(
        modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = 7.dp,
            modifier = Modifier
                .matchParentSize()
        ) {
            Box(modifier = modifier) {
                CoilImage(
                    data = product.image ?: "",
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                )
                Column(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                )
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "${product.name?.toUpperCase(Locale.ROOT)}",
                            color = Color.White,
                            style = typography.h3
                        )
                        Text(
                            text = "${product.description}",
                            color = Color.White,
                            style = typography.body2
                        )
                        Divider(
                            modifier = Modifier
                                .padding(vertical = 10.dp),
                            color = Color.LightGray,
                            thickness = 0.50.dp)
                        PriceTag(textColor = Color.Red,
                            price = product.price ?: 0)
                        PriceTag(textColor = Color.White, price = product.retailPrice ?: 0)
                    }
                }
            }
        }
    }
}

@Composable
fun PriceTag(textColor: Color, price: Long) {
    Box {
        Text(
            text = "$ ${
                NumberFormat.getNumberInstance(Locale.US).format(price)}",
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            color = textColor
        )
    }
}

@Preview
@Composable
fun PreviewProductRow(){
    val product = Product(
        id = "", name = "Jabon de avena", description = "",
        image = "https://firebasestorage.googleapis.com/v0/b/sergiomeza-e5bfa.appspot.com/o/LucesVerdes%2Fsoap_1.jpg?alt=media&token=a28af1c3-13ec-4aa6-b19f-80ab2606b5e3",
        price = 3050, retailPrice = 8000
    )
    ProductRow(product = product, modifier = Modifier)
}