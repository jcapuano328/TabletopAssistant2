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
import androidx.compose.ui.text.font.FontStyle
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
    if (die.isSpacer) {
        DieSettingsItemSpacer(
            modifier = modifier,
            onRemove = onRemove,
            enabled = enabled
        )
    } else {
        DieSettingsItemDie(
            modifier = modifier,
            die = die,
            onDieChanged = onDieChanged,
            onRemove = onRemove,
            enabled = enabled
        )
    }
}

@Composable
fun DieSettingsItemDie(
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

@Composable
fun DieSettingsItemSpacer(
    modifier: Modifier = Modifier,
    onRemove: () -> Unit,
    enabled: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        // Spacer - Remove
        // --- SPACER ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.weight(5f)
                .padding(start = 10.dp)
            //.background(Color.Gray)
        ) {
            Text("SPACER", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
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
                .build()
        )
    }

    var spacerState by remember {
        mutableStateOf(
            Die.newBuilder()
                .setIsSpacer(true)
                .build()
        )
    }

    Column() {
        DieSettingsItem(
            die = dieState,
            onDieChanged = { updatedDie ->
                // When onDieChanged is called, update the remembered state
                dieState = updatedDie
            },
            onRemove = {
                // Handle remove action if needed for preview,
                // for example, by logging or showing a placeholder
                println("Remove die button clicked in preview")
            }
        )

        DieSettingsItem(
            die = spacerState,
            onDieChanged = { updatedDie ->
                // When onDieChanged is called, update the remembered state
                spacerState = updatedDie
            },
            onRemove = {
                // Handle remove action if needed for preview,
                // for example, by logging or showing a placeholder
                println("Remove spacer button clicked in preview")
            }
        )
    }

}


