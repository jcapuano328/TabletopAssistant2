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
    onSetLeft: (Int) -> Unit = {},
    onSetRight: (Int) -> Unit = {})
{
    var isCalculatorDialogOpen by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        //Box(
        //    modifier = Modifier
        //        //.defaultMinSize(minWidth = 28.dp, minHeight = 28.dp) // Set min size
        //        //.size(28.dp)
        //        .weight(1f)
        //        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(1.dp))
        //    ,
        //    contentAlignment = Alignment.Center
        //) {
        //    Text(value.toString())
        //}
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
                    onClick = { isCalculatorDialogOpen = true }
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
    if (enabled && showCalculator && isCalculatorDialogOpen) {
        CalculatorDialog(
            Modifier.fillMaxSize(),
            onSetAttack = { value ->
                onSetLeft(value.toInt())
            },
            onSetDefend = { value ->
                onSetRight(value.toInt())
            },
            onDismissRequest = {
                isCalculatorDialogOpen = false
            }
        )
    }
}

