package com.ica.tabletopassistant.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.ica.tabletopassistant.R

@Composable
fun PngIcon(resId: Int, desc: String, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = desc,
        modifier = modifier
    )
}