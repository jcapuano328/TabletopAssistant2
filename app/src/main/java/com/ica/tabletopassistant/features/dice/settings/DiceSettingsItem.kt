package com.ica.tabletopassistant.features.dice.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ica.tabletopassistant.ui.ColorDropdown
import com.ica.tabletopassistant.data.dice.Die
import com.ica.tabletopassistant.ui.DieColorMap

@Composable
fun DieSettingsItem(
    modifier: Modifier = Modifier,
    die: Die,
    onDieChanged: (Die) -> Unit,
    onRemove: () -> Unit,
    enabled: Boolean = true
) {
    val sidesOptions = listOf(6, 8, 10)
    val selectedSides = if (die.sides in sidesOptions) die.sides else sidesOptions.first()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            //.background(Color.Blue)
    ) {

        // Sides - Die Color - Dot Color - Break After - Die Preview - Remove

        // --- SIDES SELECTOR (Segmented Buttons) ---
        Row (
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(2.5f)
            //.background(Color.Yellow)
        ) {
            sidesOptions.forEach { option ->
                val isSelected = option == selectedSides
                OutlinedButton(
                    onClick = {
                        onDieChanged(die.toBuilder().setSides(option).build())
                    },
                    enabled = enabled,
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        //.size(16.dp)
                        .weight(1f)
                        .padding(horizontal = 2.dp)
                ) {
                    Text(text = option.toString())
                }
            }
        }

        // --- DIE COLOR PICKER ---
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
            //.background(Color.Green)
        ) {
            //Text("Die", style = MaterialTheme.typography.labelSmall)
            ColorDropdown(
                selected = die.dieColor,
                available = DieColorMap,
                onSelected = {
                    onDieChanged(die.toBuilder().setDieColor(it).build())
                },
                enabled = enabled
            )
        }

        // --- DOT COLOR PICKER ---
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
            //.background(Color.Red)
        ) {
            //Text("Dot", style = MaterialTheme.typography.labelSmall)
            ColorDropdown(
                selected = die.dotColor,
                available = DieColorMap,
                onSelected = {
                    onDieChanged(die.toBuilder().setDotColor(it).build())
                },
                enabled = enabled
            )
        }

        // --- BREAK AFTER ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.5f)
            //.background(Color.Magenta)
        ) {
            //Text("Break After", style = MaterialTheme.typography.labelSmall)
            /*
            Switch(
                checked = die.breakAfter,
                onCheckedChange = { checked ->
                    onDieChanged(die.toBuilder().setBreakAfter(checked).build())
                }
            )
            */
            IconToggleButton(
                checked = die.breakAfter,
                onCheckedChange = { checked ->
                    onDieChanged(die.toBuilder().setBreakAfter(checked).build())
                },
                enabled = enabled
            ) {
                val icon = if (die.breakAfter) Icons.Default.KeyboardReturn else Icons.Default.KeyboardArrowRight
                Icon(imageVector = icon, contentDescription = "Break After")
            }

        }

        // --- DIE PREVIEW ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(0.5f)
            //.background(Color.Cyan)
        ) {
            com.ica.tabletopassistant.ui.Die(
                modifier = Modifier.size(40.dp),
                dieNumber = 1,
                onDieClicked = {},
                sides = die.sides,
                dieColor = DieColorMap[die.dieColor] ?: Color.White,
                dotColor = DieColorMap[die.dotColor] ?: Color.Black,
                dieValue = 1 // Always show 1 for preview
            )
        }

        // --- REMOVE BUTTON ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(0.5f)
            //.background(Color.Gray)
        ) {
            IconButton(
                onClick = onRemove,
                enabled = enabled
            ) {
                Icon(
                    imageVector = Icons.Default.Delete, // Replace with your asset
                    contentDescription = "Remove Die",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDieSettingsItem() {
    // Remember the state of the die within the preview
    var dieState by remember {
        mutableStateOf(
            Die.newBuilder()
                .setSides(6)
                .setDieColor("red")
                .setDotColor("white")
                .setCurrentValue(1)
                .setBreakAfter(false)
                .build()
        )
    }

    DieSettingsItem(
        die = dieState,
        onDieChanged = { updatedDie ->
            // When onDieChanged is called, update the remembered state
            dieState = updatedDie
        },
        onRemove = {
            // Handle remove action if needed for preview,
            // for example, by logging or showing a placeholder
            println("Remove button clicked in preview")
        }
    )
}



/*
@Composable
fun DieSettingsItem(
    die: Die,
    onDieChanged: (Die) -> Unit,
    onRemove: () -> Unit
) {
    val sides = remember(die.sides) { mutableStateOf(die.sides.toString()) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(8.dp)) {
            OutlinedTextField(
                value = sides.value,
                onValueChange = {
                    sides.value = it
                    it.toIntOrNull()?.let { s ->
                        onDieChanged(die.toBuilder().setSides(s).build())
                    }
                },
                label = { Text("Sides") },
                modifier = Modifier.fillMaxWidth()
            )

            // Placeholder for color pickers
            Text("Color: ${die.dieColor}, Dots: ${die.dotColor}")

            Spacer(Modifier.height(4.dp))
            Button(
                onClick = onRemove,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Remove")
            }
        }
    }
}
*/

/*
Text("Die Color:")
ColorPickerRow(
    selectedColor = die.dieColor,
    onColorSelected = { newColor ->
        val updated = die.toBuilder().setDieColor(newColor).build()
        onDieChanged(updated)
    }
)

Spacer(Modifier.height(16.dp))

Text("Dot Color:")
ColorPickerRow(
    selectedColor = die.dotColor,
    onColorSelected = { newColor ->
        val updated = die.toBuilder().setDotColor(newColor).build()
        onDieChanged(updated)
    }
)
*/

/*
@Preview(showBackground = true)
@Composable
fun PreviewDieSettingsItem() {
    DieSettingsItem(
        die = Die.newBuilder()
            .setSides(6)
            .setDieColor("red")
            .setDotColor("white")
            .setCurrentValue(1)
            .setBreakAfter(false)
            .build(),
        onDieChanged = {},
        onRemove = {}
    )
}
*/