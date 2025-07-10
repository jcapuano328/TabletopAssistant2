package com.ica.tabletopassistant.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InputIntegerWithIncrementDecrement(
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
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            onClick = { if (value - step >= minValue) onValueChange(value - step) },
            enabled = value - step >= minValue
        ) {
            Icon(Icons.Default.Remove, contentDescription = "Decrement")
        }

        OutlinedTextField(
            value = value.toString(),
            onValueChange = {
                val newValue = it.toIntOrNull()?.coerceIn(minValue, maxValue)
                if (newValue != null) {
                    onValueChange(newValue)
                }
            },
            label = { Text(label) },
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(120.dp)
        )

        IconButton(
            onClick = { if (value + step <= maxValue) onValueChange(value + step) },
            enabled = value + step <= maxValue
        ) {
            Icon(Icons.Default.Add, contentDescription = "Increment")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIntegerInput() {
    var value by remember { mutableStateOf(5) }
    InputIntegerWithIncrementDecrement(
        value = value,
        onValueChange = { value = it },
        label = "Count",
    )
}