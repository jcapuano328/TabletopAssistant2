package com.ica.tabletopassistant.features.spinners.feature

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.ica.tabletopassistant.ui.PngIcon
import com.ica.tabletopassistant.features.calculator.CalculatorDialog

@Composable
fun SpinnerDifference(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    value: Int,
    showCalculator: Boolean,
    onShowCalculator: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = value.toString(),
            modifier = Modifier
                .border(width = 1.dp, color = Color.DarkGray)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
        if (showCalculator) {
            IconButton(
                enabled = enabled,
                onClick = onShowCalculator
            ) {
                PngIcon(
                    modifier = modifier.size(26.dp),
                    resId = com.ica.tabletopassistant.R.drawable.calc,
                    desc = "Calculator"
                )
            }
        }
    }
}

