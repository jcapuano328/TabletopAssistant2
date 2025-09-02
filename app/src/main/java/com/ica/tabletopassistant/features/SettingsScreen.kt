package com.ica.tabletopassistant.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.ica.tabletopassistant.R;
import com.ica.tabletopassistant.data.dice.Die
import com.ica.tabletopassistant.ui.PngIcon
import com.ica.tabletopassistant.features.dice.settings.DiceSettings
import com.ica.tabletopassistant.features.dice.settings.DiceSettingsContent
import com.ica.tabletopassistant.features.dice.settings.DiceSettingsUiState
import com.ica.tabletopassistant.features.general.SettingsHelpContent
import com.ica.tabletopassistant.features.general.TabletopHelpContent
import com.ica.tabletopassistant.features.help.HelpDialog
import com.ica.tabletopassistant.features.odds.settings.OddsSettings
import com.ica.tabletopassistant.features.odds.settings.OddsSettingsContent
import com.ica.tabletopassistant.features.odds.settings.OddsSettingsUiState
import com.ica.tabletopassistant.features.spinners.settings.SpinnersSettings
import com.ica.tabletopassistant.features.spinners.settings.SpinnersSettingsContent
import com.ica.tabletopassistant.features.spinners.settings.SpinnersSettingsUiState
import com.ica.tabletopassistant.ui.theme.TabletopAssistantTheme

// SettingsScreen.kt

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
    // No need for ViewModel here if all VMs are in children
) {
    SettingsScreenContent(
        onBackClick = onBackClick,
        onResetClick = {},
        diceSettingsSection = { modifier, onRegisterReset ->
            DiceSettings(modifier = modifier, onRegisterReset = onRegisterReset) // The real, Hilt-powered composable
        },
        oddsSettingsSection = { modifier, onRegisterReset ->
            OddsSettings(modifier = modifier, onRegisterReset = onRegisterReset) // The real, Hilt-powered composable
        },
        spinnersSettingsSection = { modifier, onRegisterReset ->
            SpinnersSettings(modifier = modifier, onRegisterReset = onRegisterReset) // The real, Hilt-powered composable
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    onBackClick: () -> Unit,
    onResetClick: () -> Unit,
    diceSettingsSection: @Composable (Modifier, onRegisterReset: ((() -> Unit) -> Unit)) -> Unit,
    oddsSettingsSection: @Composable (Modifier, onRegisterReset: ((() -> Unit) -> Unit)) -> Unit,
    spinnersSettingsSection: @Composable (Modifier, onRegisterReset: ((() -> Unit) -> Unit)) -> Unit,
    modifier: Modifier = Modifier
) {
    val resetCallbacks = remember { mutableStateListOf<() -> Unit>() }
    var showHelp by remember { mutableStateOf(false) }

    Card(modifier = modifier.padding(2.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize().padding(2.dp)
        ) {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                title = { Text("Settings") },
                actions = {
                    IconButton(onClick = {
                        onResetClick()
                        resetCallbacks.forEach { it() }
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset")
                    }
                    IconButton(onClick = { showHelp = true }) {
                        Icon(Icons.Default.HelpOutline, contentDescription = "Help")
                    }
                }
            )

            // Invoke the provided composable slots, passing the necessary modifier
            diceSettingsSection(Modifier.weight(4f), { callback -> resetCallbacks.add(callback)})

            Spacer(Modifier.height(24.dp))

            oddsSettingsSection(Modifier.weight(1f), { callback -> resetCallbacks.add(callback)})

            Spacer(Modifier.height(24.dp))

            spinnersSettingsSection(Modifier.weight(1f), { callback -> resetCallbacks.add(callback)})
        }
    }

    if (showHelp) {
        HelpDialog(
            onDismiss = {showHelp = false},
            currentTopic = "Settings"
        ) {
            SettingsHelpContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    var previewStateDice by remember {
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
                        .build(),
                    Die.newBuilder()
                        .setSides(6)
                        .setDieColor("black")
                        .setDotColor("white")
                        .setCurrentValue(1)
                        .build(),
                    Die.newBuilder()
                        .setSides(6)
                        .setDieColor("black")
                        .setDotColor("red")
                        .setCurrentValue(1)
                        .build()
                )
            )
        )
    }

    var previewStateOdds by remember {
        mutableStateOf(
            OddsSettingsUiState(
                isEnabled = true,
                isRounded = true,
                roundingMode = 1,
                attack = "1",
                defend = "1"
            )
        )
    }

    var previewStateSpinners by remember {
        mutableStateOf(
            SpinnersSettingsUiState(
                isEnabled = true,
                number  = 2,
                followDice  = false,
                calcDifference  = true,
                showCalculator  = false,
                values = emptyList()
            )
        )
    }

    TabletopAssistantTheme {
        SettingsScreenContent(
            onBackClick = {},
            onResetClick = {
                // Reset all settings to their default values
                previewStateDice = DiceSettingsUiState(
                    isEnabled = true,
                    isOneBased = false,
                    dice = emptyList()
                )
                previewStateOdds = OddsSettingsUiState(
                    isEnabled = false,
                    isRounded = false,
                    roundingMode = 1,
                    attack = "1",
                    defend = "1"
                )
                previewStateSpinners = SpinnersSettingsUiState(
                    isEnabled = false,
                    number  = 1,
                    followDice  = false,
                    calcDifference  = false,
                    showCalculator  = false,
                )
            },
            diceSettingsSection = { modifier, onRegisterReset ->
                DiceSettingsContent(
                    modifier = modifier,
                    state = previewStateDice,

                    onAddDie = {
                        // Create a new die and add it to the list
                        val newDie = Die.newBuilder()
                            .setSides(6) // Default values for a new die
                            .setDieColor("red")
                            .setDotColor("white")
                            .setCurrentValue(1)
                            .build()
                        previewStateDice =
                            previewStateDice.copy(dice = previewStateDice.dice + newDie)
                    },
                    onAddSpacer = {
                        // Create a new spacer and add it to the list
                        val newSpacer = Die.newBuilder()
                            .setIsSpacer(true)
                            .build()
                        previewStateDice =
                            previewStateDice.copy(dice = previewStateDice.dice + newSpacer)
                    },
                    onRemoveDie = { index ->
                        // Remove the die at the specified index
                        previewStateDice =
                            previewStateDice.copy(dice = previewStateDice.dice.filterIndexed { i, _ -> i != index })
                    },
                    onUpdateDie = { index, updatedDie ->
                        // Update the die at the specified index
                        val updatedDice = previewStateDice.dice.toMutableList()
                        if (index in updatedDice.indices) {
                            updatedDice[index] = updatedDie
                        }
                        previewStateDice = previewStateDice.copy(dice = updatedDice)
                    },
                    onToggleEnabled = { isEnabled ->
                        // Toggle the isEnabled flag
                        previewStateDice = previewStateDice.copy(isEnabled = isEnabled)
                    },
                    onToggleOneBased = { isOneBased ->
                        // Toggle the isOneBased flag
                        previewStateDice = previewStateDice.copy(isOneBased = isOneBased)
                    }
                )
            },
            oddsSettingsSection = { modifier, onRegisterReset ->
                OddsSettingsContent(
                    modifier = modifier,
                    state = previewStateOdds,
                    onToggleIsEnabled = { isEnabled ->
                        previewStateOdds = previewStateOdds.copy(isEnabled = isEnabled)
                    },
                    onToggleIsRounded = { isRounded ->
                        previewStateOdds = previewStateOdds.copy(isRounded = isRounded)
                    },
                    onUpdateRoundingMode = { mode ->
                        previewStateOdds = previewStateOdds.copy(roundingMode = mode)
                    }
                )
            },
            spinnersSettingsSection = { modifier, onRegisterReset ->
                SpinnersSettingsContent(
                    state = previewStateSpinners,
                    onToggleIsEnabled = { isEnabled ->
                        previewStateSpinners = previewStateSpinners.copy(isEnabled = isEnabled)
                    },
                    onUpdateNumber = { number ->
                        previewStateSpinners = previewStateSpinners.copy(number = number)
                    },
                    onToggleFollowDice = { followDice ->
                        previewStateSpinners =
                            previewStateSpinners.copy(followDice = followDice)
                    },
                    onToggleShowDifference = { calcDifference ->
                        previewStateSpinners =
                            previewStateSpinners.copy(calcDifference = calcDifference)
                    },
                    onToggleShowCalculator = { showCalculator ->
                        previewStateSpinners =
                            previewStateSpinners.copy(showCalculator = showCalculator)
                    }
                )
            }
        )
    }
}