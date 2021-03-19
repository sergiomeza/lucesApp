package com.lucesapp.ui.views

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lucesapp.R
import com.lucesapp.utils.TextFieldState
import com.lucesapp.utils.TextState

@ExperimentalAnimationApi
@Composable
fun ProductForm(
    onFormSubmitted: (name: String, description: String, price: String, retail: String) -> Unit,
    isLoading: State<Boolean?>){
    val nameFocusRequest = remember { FocusRequester() }
    val priceFocusRequest = remember { FocusRequester() }
    val descriptionFocusRequest = remember { FocusRequester() }
    val priceRetailFocusRequest = remember { FocusRequester() }
    Column(modifier = Modifier.fillMaxWidth()) {
        //Name Input
        val nameState = remember { TextState() }
        CustomTextField(
            label = stringResource(id = R.string.product_name),
            onImeAction = { descriptionFocusRequest.requestFocus() },
            state = nameState,
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(16.dp))
        //Description Input
        val descriptionState = remember { TextState() }
        CustomTextField(
            label = stringResource(id = R.string.product_description),
            onImeAction = { priceFocusRequest.requestFocus() },
            state = descriptionState,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(16.dp))
        //Price Input
        val priceState = remember { TextState() }
        CustomTextField(
            label = stringResource(id = R.string.product_price),
            onImeAction = { priceRetailFocusRequest.requestFocus() },
            state = priceState,
            imeOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(16.dp))
        //Retail Input
        val priceRetailState = remember { TextState() }
        CustomTextField(
            label = stringResource(id = R.string.product_price_retail),
            state = priceRetailState,
            imeOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            imeAction = ImeAction.Done,
            onImeAction = {
                onFormSubmitted(
                    nameState.text,
                    descriptionState.text,
                    priceState.text,
                    priceRetailState.text
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onFormSubmitted(
                    nameState.text,
                    descriptionState.text,
                    priceState.text,
                    priceRetailState.text
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = nameState.isValid &&
                    priceState.isValid && priceRetailState.isValid && !isLoading.value!!
        ) {
            Text(text = stringResource(id = R.string.product_create))
        }
    }
}

@Composable
fun CustomTextField(
    label: String,
    state: TextFieldState = remember { TextFieldState() },
    imeOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text),
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = state.text,
        onValueChange = {
            state.text = it
        },
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                val focused = focusState == FocusState.Active
                state.onFocusChange(focused)
                if (!focused) {
                    state.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        isError = state.showErrors(),
        keyboardOptions = imeOptions.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            },
        )
    )
    state.getError()?.let { error -> TextFieldError(textError = error) }
}

/**
 * To be removed when [TextField]s support error
 */
@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
        )
    }
}