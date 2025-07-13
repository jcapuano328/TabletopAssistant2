package com.ica.tabletopassistant.features.spinners.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.ica.tabletopassistant.features.spinners.settings.SpinnersSettingsViewModel
import com.ica.tabletopassistant.ui.theme.TabletopAssistantTheme

@Composable
fun SpinnersSettings(
    modifier: Modifier = Modifier,
    onRegisterReset: ((() -> Unit) -> Unit),
    viewModel: SpinnersSettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Register once
    LaunchedEffect(Unit) {
        onRegisterReset {
            viewModel.reset()
        }
    }

    SpinnersSettingsContent(
        modifier = modifier,
        state = uiState,
        onToggleIsEnabled = viewModel::setIsEnabled,
        onUpdateNumber = viewModel::setNumber,
        onToggleFollowDice = viewModel::setFollowDice,
        onToggleShowDifference = viewModel::setCalcDifference,
        onToggleShowCalculator = viewModel::setShowCalculator
    )
}


@Composable
fun SpinnersSettingsContent(
    modifier: Modifier = Modifier,
    state: SpinnersSettingsUiState,
    onToggleIsEnabled: (Boolean) -> Unit,
    onUpdateNumber: (Int) -> Unit,
    onToggleFollowDice: (Boolean) -> Unit,
    onToggleShowDifference: (Boolean) -> Unit,
    onToggleShowCalculator: (Boolean) -> Unit
) {
    val maxSpinners = 5
    val number = state.number.coerceIn(0, maxSpinners)

    Column(
        //modifier = modifier.fillMaxWidth().padding(16.dp)
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
                text = "Spinners",
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp, textAlign = TextAlign.Center)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            // -- ENABLED --
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.weight(0.5f)
            ) {
                //Text("Enabled")
                Switch(
                    checked = state.isEnabled,
                    onCheckedChange = onToggleIsEnabled
                )
            }

            // -- NUMBER --
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.weight(2f)
            ) {

                //Text("Number of Spinners: $number")
                Text("$number")
                Slider(
                    value = number.toFloat(),
                    onValueChange = { value ->
                        onUpdateNumber(value.toInt())
                    },
                    valueRange = 0f..maxSpinners.toFloat(),
                    steps = maxSpinners - 1,
                    enabled = state.isEnabled,
                    modifier = modifier.padding(start = 8.dp, end = 8.dp)
                )
            }

            // -- FLAGS --
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = modifier.weight(1f)
            ) {
                // -- FOLLOW DICE --
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Checkbox(
                        checked = state.followDice,
                        onCheckedChange = onToggleFollowDice,
                        enabled = state.isEnabled,
                        modifier = Modifier.size(24.dp)
                    )
                    Text("Follow Dice")
                }
                // -- SHOW DIFFERENCE --
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Checkbox(
                        checked = state.calcDifference,
                        onCheckedChange = onToggleShowDifference,
                        enabled = state.isEnabled,
                        modifier = Modifier.size(24.dp)
                    )
                    Text("Difference")
                }

                // -- SHOW CALCULATOR --
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Checkbox(
                        checked = state.showCalculator,
                        onCheckedChange = onToggleShowCalculator,
                        enabled = state.isEnabled,
                        modifier = Modifier.size(24.dp)
                    )
                    Text("Calculator")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSpinnersSettings() {
    // Use remember to store the state locally within the preview
    var previewState by remember {
        mutableStateOf(
            SpinnersSettingsUiState(
                isEnabled = true,
                number  = 0,
                followDice  = false,
                calcDifference  = false,
                showCalculator  = false,
                values = emptyList()
            )
        )
    }
    TabletopAssistantTheme {
        SpinnersSettingsContent(
            state = previewState,
            onToggleIsEnabled = { isEnabled ->
                previewState = previewState.copy(isEnabled = isEnabled)
            },
            onUpdateNumber = { number ->
                previewState = previewState.copy(number = number)
            },
            onToggleFollowDice = { followDice ->
                previewState = previewState.copy(followDice = followDice)
            },
            onToggleShowDifference = { calcDifference ->
                previewState = previewState.copy(calcDifference = calcDifference)
            },
            onToggleShowCalculator = { showCalculator ->
                previewState = previewState.copy(showCalculator = showCalculator)
            }
        )
    }
}