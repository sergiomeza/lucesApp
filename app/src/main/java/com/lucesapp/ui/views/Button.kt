package com.lucesapp.ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun PrimaryButton(
    caption: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) = BaseButton(caption = caption, onClick = onClick, modifier = modifier)

@Composable
fun SecondaryButton(
    caption: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) = BaseButton(
    caption = caption,
    onClick = onClick,
    modifier = modifier,
    color = MaterialTheme.colors.secondary
)

@Composable
fun BaseButton(
    caption: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(45.dp)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color ?: MaterialTheme.colors.primary
        )
    ) {
        Text(caption.toUpperCase(Locale.getDefault()), style = MaterialTheme.typography.button)
    }
}