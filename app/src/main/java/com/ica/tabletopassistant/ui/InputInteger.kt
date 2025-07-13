package com.ica.tabletopassistant.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InputInteger(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Enter Value",
    minValue: Int = 0,
    maxValue: Int = Int.MAX_VALUE,
    step: Int = 1,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        OutlinedIconButton(
            modifier = Modifier
                .defaultMinSize(minWidth = 28.dp, minHeight = 28.dp) // Set min size
                .size(28.dp)
            ,
            shape = RoundedCornerShape(5.dp),
            onClick = { if (value - step >= minValue) onValueChange(value - step) },
            enabled = value - step >= minValue
        ) {
            Icon(Icons.Default.Remove, contentDescription = "Decrement", modifier = Modifier.fillMaxSize().background(color = Color.LightGray))
        }

        Box(
            modifier = Modifier
                .defaultMinSize(minWidth = 28.dp, minHeight = 28.dp) // Set min size
                .size(28.dp)
                .weight(1f)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(1.dp))
            ,
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = value.toString(),
                onValueChange = {
                    val newValue = it.toIntOrNull()?.coerceIn(minValue, maxValue)
                    if (newValue != null) {
                        onValueChange(newValue)
                    }
                },
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                //label = { Text(label, textAlign = TextAlign.Center) },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth() // TextField fills the Box
                //.border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(1.dp))
            )
        }
        OutlinedIconButton(
            modifier = Modifier
                .defaultMinSize(minWidth = 28.dp, minHeight = 28.dp) // Set min size
                .size(28.dp)
            ,
            shape = RoundedCornerShape(5.dp),
            onClick = { if (value + step <= maxValue) onValueChange(value + step) },
            enabled = value + step <= maxValue
        ) {
            Icon(Icons.Default.Add, contentDescription = "Increment", modifier = Modifier.fillMaxSize().background(color = Color.LightGray))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIntegerInput() {
    var value1 by remember { mutableStateOf(5) }
    var value2 by remember { mutableStateOf(13) }

    Row (
        //modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InputInteger(
            modifier = Modifier.weight(1f),
            value = value1,
            onValueChange = { value1 = it },
            label = "Count",
        )

        InputInteger(
            modifier = Modifier.weight(1f),
            value = value2,
            onValueChange = { value2 = it },
            label = "Count",
        )

    }
}