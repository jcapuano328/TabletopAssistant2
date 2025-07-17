package com.ica.tabletopassistant.features.spinners.feature

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.ica.tabletopassistant.ui.PngIcon
import com.ica.tabletopassistant.ui.CalculatorDialog

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
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(value.toString())
        }

        Row(
            modifier = modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
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
}

