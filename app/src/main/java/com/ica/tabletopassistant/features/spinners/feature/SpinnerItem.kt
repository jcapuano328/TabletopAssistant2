package com.ica.tabletopassistant.features.spinners.feature

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.ica.tabletopassistant.ui.PngIcon
import com.ica.tabletopassistant.ui.InputInteger

@Composable
fun SpinnerItem(modifier: Modifier = Modifier, value: Int, onChanged: (Int) -> Unit) {
    Row(
        modifier = modifier.padding(start = 2.dp, end = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier.weight(3f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            InputInteger(
                label = "",
                value = value,
                onValueChange = onChanged
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = { onChanged(0) }
            ) {
                PngIcon(
                    modifier = modifier.size(24.dp),
                    resId = com.ica.tabletopassistant.R.drawable.reset,
                    desc = "Reset"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSpinnerItem() {
    var previewValue1 by remember { mutableStateOf(5) }
    var previewValue2 by remember { mutableStateOf(13) }

    Row (
        //modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SpinnerItem(
            modifier = Modifier.weight(1f),
            value = previewValue1,
            onChanged = { previewValue1 = it }
        )
        SpinnerItem(
            modifier = Modifier.weight(1f),
            value = previewValue2,
            onChanged = { previewValue2 = it }
        )
    }
}
