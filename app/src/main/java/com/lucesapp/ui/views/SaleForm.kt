package com.lucesapp.ui.views

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.graphics.rotationMatrix
import com.lucesapp.R
import com.lucesapp.model.Product
import com.lucesapp.ui.screens.PriceTag
import com.lucesapp.ui.theme.gray800
import com.lucesapp.ui.theme.gray900
import com.lucesapp.ui.theme.typography
import com.lucesapp.utils.TextState
import java.util.Collections.rotate
import kotlin.math.log

@ExperimentalAnimationApi
@Composable
fun SaleForm(
    onFormSubmitted: (product: Product?, quantity: Int) -> Unit,
    isLoading: State<Boolean?>,
    products: List<Product>?
){
    val productFocusRequest = remember { FocusRequester() }
    val quantityFocusRequest = remember { FocusRequester() }
    Column(modifier = Modifier.fillMaxWidth()) {
        val productIndexState = remember { mutableStateOf(-1) }
        val quantityState = remember { TextState() }

        DropdownProducts(products = products, onProductSelected = {
            productIndexState.value = it
        })
        Spacer(modifier = Modifier.height(16.dp))
        //Quantity input
        CustomTextField(
            label = stringResource(id = R.string.sale_quantity),
            state = quantityState,
            imeOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            imeAction = ImeAction.Done,
            onImeAction = {
                onFormSubmitted(products?.get(productIndexState.value), quantityState.text.toInt())
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onFormSubmitted(products?.get(productIndexState.value), quantityState.text.toInt())
            },
            modifier = Modifier.fillMaxWidth()
                .size(46.dp),
            enabled = productIndexState.value >= 0 &&
                    quantityState.isValid && !isLoading.value!!
        ) {
            Text(text = stringResource(id = R.string.sale_create))
        }
    }
}

@Composable
fun DropdownProducts(
    products: List<Product>?,
    onProductSelected: (index: Int) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
    val rotation = if (expanded.value) 180F else 0F
    val selectedIndex = remember { mutableStateOf(-1) }
    val selectedProductText = remember { mutableStateOf("") }
    if (selectedIndex.value >= 0){
        selectedProductText.value = products?.get(selectedIndex.value)?.name ?: ""
    }
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.product_name),
                style = typography.caption
            )
            Spacer(modifier = Modifier.height(6.dp))
            Card(
                modifier = Modifier
                    .height(46.dp)
                    .clickable(onClick = { expanded.value = true })
                    .background(
                        MaterialTheme.colors.surface
                    ),
                elevation = 4.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colors.surface
                        )
                ) {
                    Text(
                        text = if (selectedIndex.value >= 0) selectedProductText.value else stringResource(
                            id = R.string.product_select
                        ),
                        style = if (selectedIndex.value >= 0) MaterialTheme.typography.body1 else MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        color = if (selectedIndex.value >= 0) gray900 else gray800
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = stringResource(R.string.app_name),
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .rotate(rotation)
                    )
                }
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colors.surface
                    )
            ) {
                products?.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        selectedIndex.value = index
                        expanded.value = false
                        onProductSelected(selectedIndex.value)
                    }) {
                        Text(text = s.name ?: "", style = MaterialTheme.typography.body1)
                    }
                }
            }
        }
    }
}