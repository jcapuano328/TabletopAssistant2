package com.ica.tabletopassistant.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ColorDropdown(
    selected: String,
    available: Map<String, Color>,
    onSelected: (String) -> Unit,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(
            enabled = enabled,
            onClick = { expanded = true },
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp) // Example smaller padding
        )  {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(DieColorMap[selected] ?: Color.Gray)
                        .border(1.dp, Color.Black)
                )
                //Spacer(Modifier.width(8.dp))
                //Text(selected)
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            available.keys.forEach { colorName ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(available[colorName] ?: Color.Gray)
                                    .border(1.dp, Color.Black)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(colorName)
                        }
                    },
                    onClick = {
                        expanded = false
                        onSelected(colorName)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewColorDropdown() {
    ColorDropdown(selected = "red", available = DieColorMap, onSelected = {})
}
