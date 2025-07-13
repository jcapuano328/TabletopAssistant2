package com.ica.tabletopassistant.features.dice.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn // Import LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed // Import itemsIndexed for LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.SpaceBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ica.tabletopassistant.ui.ColorDropdown
import com.ica.tabletopassistant.data.dice.Die
import com.ica.tabletopassistant.ui.DieColorMap
import com.ica.tabletopassistant.ui.PngIcon
import com.ica.tabletopassistant.ui.theme.TabletopAssistantTheme

@Composable
fun DiceSettings(
    modifier: Modifier = Modifier,
    onRegisterReset: ((() -> Unit) -> Unit),
    viewModel: DiceSettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Register once
    LaunchedEffect(Unit) {
        onRegisterReset {
            viewModel.reset()
        }
    }

    DiceSettingsContent(
        modifier = modifier,
        state = uiState,
        onAddDie = viewModel::addDie,
        onAddSpacer = viewModel::addSpacer,
        onRemoveDie = viewModel::removeDie,
        onUpdateDie = viewModel::updateDie,
        onToggleEnabled = viewModel::setEnabled,
        onToggleOneBased = viewModel::setIsOneBased
    )
}

@Composable
fun DiceSettingsContent(
    modifier: Modifier = Modifier,
    state: DiceSettingsUiState,
    onAddDie: () -> Unit,
    onAddSpacer: () -> Unit,
    onRemoveDie: (Int) -> Unit,
    onUpdateDie: (index: Int, updated: Die) -> Unit,
    onToggleEnabled: (Boolean) -> Unit,
    onToggleOneBased: (Boolean) -> Unit
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(2.dp)
    ) {
        // -- HEADER --
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                //.background(Color.LightGray)
                .background(MaterialTheme.colorScheme.secondary)
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dice",
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp, textAlign = TextAlign.Center)
            )
        }

        // -- FLAGS --
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            // -- ENABLED --
            //Text("Enabled")
            Switch(
                checked = state.isEnabled,
                onCheckedChange = onToggleEnabled
            )
            // -- ONE BASED --
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier.weight(2f)
            ) {
                //Switch(
                Checkbox(
                    checked = state.isOneBased,
                    onCheckedChange = onToggleOneBased,
                    enabled = state.isEnabled
                )
                Text("One-Based")
            }

            // -- ADD DIE / SPACE --
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.padding(end = 4.dp)
            ) {
                Button(
                    onClick = onAddDie,
                    enabled = state.isEnabled
                ) {
                    Icon(Icons.Default.Casino, contentDescription = "Add Die")
                    //Spacer(modifier = Modifier.width(8.dp))
                    //Text("Add Die")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Button(
                    onClick = onAddSpacer,
                    enabled = state.isEnabled
                ) {
                    Icon(Icons.Default.SpaceBar, contentDescription = "Add Spacer")
                    //Spacer(modifier = Modifier.width(8.dp))
                    //Text("Add Spacer")
                }
                /*
                IconButton(
                    onClick = onAddDie,
                    enabled = state.isEnabled
                    //modifier = Modifier
                    //    .align(Alignment.Center)
                ) {
                    PngIcon(
                        resId = com.ica.tabletopassistant.R.drawable.add,
                        desc = "Add Die",
                        modifier = Modifier
                            .size(40.dp)
                        //.align(Alignment.Center)
                    )
                }
                //Text(text = "Add Die")

                IconButton(
                    onClick = onAddSpacer,
                    enabled = state.isEnabled
                    //modifier = Modifier
                    //    .align(Alignment.Center)
                ) {
                    PngIcon(
                        resId = com.ica.tabletopassistant.R.drawable.spacer,
                        desc = "Add Space",
                        modifier = Modifier
                            .size(40.dp)
                        //.align(Alignment.Center)
                    )
                }
                //Text(text = "Add Spacer")
                */

            }
        }

        Spacer(Modifier.height(4.dp))

        // -- DICE HEADER --
        Row(

        ) {
            Text(
                text = "Sides",
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp, textAlign = TextAlign.Center),
                modifier = Modifier.weight(2.5f)
            )
            Text(
                text = "Die",
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp, textAlign = TextAlign.Center),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Dot",
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp, textAlign = TextAlign.Center),
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.weight(1f))
        }

        // -- DICE --
        // Use LazyColumn for the list of dice
        LazyColumn(modifier = Modifier.weight(1f)) { // Use weight to fill available space if needed
            itemsIndexed(state.dice) { index, die ->
                DieSettingsItem(
                    die = die,
                    onDieChanged = { updated -> onUpdateDie(index, updated) },
                    onRemove = { onRemoveDie(index) },
                    enabled = state.isEnabled
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDiceSettings() {
    // Use remember to store the state locally within the preview
    var previewState by remember {
        mutableStateOf(
            DiceSettingsUiState(
                isEnabled = true,
                isOneBased = false,
                dice = listOf(
                    Die.newBuilder()
                        .setSides(6)
                        .setDieColor("red")
                        .setDotColor("white")
                        .setCurrentValue(1)
                        .build(),
                    Die.newBuilder()
                        .setSides(6)
                        .setDieColor("white")
                        .setDotColor("black")
                        .setCurrentValue(1)
                        .build(),
                    Die.newBuilder()
                        .setIsSpacer(true)
                        .build()
                )
            )
        )
    }

    TabletopAssistantTheme {
        DiceSettingsContent(
            state = previewState,
            onAddDie = {
                // Create a new die and add it to the list
                val newDie = Die.newBuilder()
                    .setSides(6) // Default values for a new die
                    .setDieColor("red")
                    .setDotColor("white")
                    .setCurrentValue(1)
                    .build()
                previewState = previewState.copy(dice = previewState.dice + newDie)
            },
            onAddSpacer = {
                // Create a new spacer and add it to the list
                val newSpacer = Die.newBuilder()
                    .setIsSpacer(true)
                    .build()
                previewState = previewState.copy(dice = previewState.dice + newSpacer)
            },
            onRemoveDie = { index ->
                // Remove the die at the specified index
                previewState =
                    previewState.copy(dice = previewState.dice.filterIndexed { i, _ -> i != index })
            },
            onUpdateDie = { index, updatedDie ->
                // Update the die at the specified index
                val updatedDice = previewState.dice.toMutableList()
                if (index in updatedDice.indices) {
                    updatedDice[index] = updatedDie
                }
                previewState = previewState.copy(dice = updatedDice)
            },
            onToggleEnabled = { isEnabled ->
                // Toggle the isEnabled flag
                previewState = previewState.copy(isEnabled = isEnabled)
            },
            onToggleOneBased = { isOneBased ->
                // Toggle the isOneBased flag
                previewState = previewState.copy(isOneBased = isOneBased)
            }
        )
    }
}