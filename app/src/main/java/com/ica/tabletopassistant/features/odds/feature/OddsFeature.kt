package com.ica.tabletopassistant.features.odds.feature

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
import com.ica.tabletopassistant.ui.InputNumeric
import com.ica.tabletopassistant.ui.PngIcon
import com.ica.tabletopassistant.features.calculator.CalculatorDialogType
import com.ica.tabletopassistant.ui.theme.TabletopAssistantTheme

@Composable
fun OddsFeature(
        modifier: Modifier = Modifier,
        viewModel: OddsFeatureViewModel = hiltViewModel(),
        showDialog: (type: CalculatorDialogType, isRounded: Boolean, roundingMode: Int, initial: Float, onSetAttack: (Float) -> Unit, onSetDefend: (Float) -> Unit) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    OddsFeatureContent(
        modifier = modifier,
        state = uiState,
        onUpdateAttack = viewModel::setAttack,
        onUpdateDefend = viewModel::setDefend,
        onShowCacluator = {
            showDialog(CalculatorDialogType.Odds, uiState.isRounded, uiState.roundingMode, /*uiState.attack*/0f, { viewModel.setAttack(it.toString()) }, { viewModel.setDefend(it.toString()) })
        }

    )
}

@Composable
fun OddsFeatureContent(
    modifier: Modifier = Modifier,
    state: OddsFeatureUiState,
    onUpdateAttack: (String) -> Unit = {},
    onUpdateDefend: (String) -> Unit = {},
    onShowCacluator: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(2.dp)
    ) {
        /*
        Text("Enabled: ${state.isEnabled}")
        Text("Rounded: ${state.isRounded}")
        Text("Rounding Mode: ${state.roundingMode}")
        Text("Attack: ${state.attack}")
        Text("Defend: ${state.defend}")
        Text("Odds: ${state.odds}")
        */
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
            ,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Attacker",
                style = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 12.sp, textAlign = TextAlign.Center),
                modifier = Modifier.weight(1f)
            )
            Box(modifier = Modifier.weight(0.5f)) {
                IconButton(
                    onClick = {
                        onShowCacluator()
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    PngIcon(
                        resId = com.ica.tabletopassistant.R.drawable.calc,
                        desc = "Calculator",
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.Center)
                    )
                }

            }
            Text(
                text = "Defender",
                style = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 12.sp, textAlign = TextAlign.Center),
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputNumeric(
                value = state.attack,
                onValueChange = onUpdateAttack,
                label = "Attacker",
                modifier = Modifier.weight(1f)
            )
            Text(text = state.odds, modifier = Modifier.weight(0.5f), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            InputNumeric(
                value = state.defend,
                onValueChange = onUpdateDefend,
                label = "Defender",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOddsFeature() {
    // Use remember to store the state locally within the preview
    var previewState by remember {
        mutableStateOf(
            OddsFeatureUiState(
                isEnabled = true,
                isRounded = true,
                roundingMode = 1,
                attack = "13.5",
                defend = "4.36"
            )
        )
    }

    previewState = previewState.copy(odds = com.ica.tabletopassistant.util.calcOdds(previewState.attack.toFloat(), previewState.defend.toFloat(), previewState.isRounded, previewState.roundingMode))

    var isCalculatorDialogOpen by remember { mutableStateOf(false) }

    TabletopAssistantTheme {
        OddsFeatureContent(
            state = previewState,
            onUpdateAttack = { attack ->
                previewState = previewState.copy(
                    attack = attack,
                    odds = com.ica.tabletopassistant.util.calcOdds(
                        attack.toFloat(),
                        previewState.defend.toFloat(),
                        previewState.isRounded,
                        previewState.roundingMode
                    )
                )
            },
            onUpdateDefend = { defend ->
                previewState = previewState.copy(
                    defend = defend,
                    odds = com.ica.tabletopassistant.util.calcOdds(
                        previewState.attack.toFloat(),
                        defend.toFloat(),
                        previewState.isRounded,
                        previewState.roundingMode
                    )
                )
            },
            onShowCacluator = {
                isCalculatorDialogOpen = true
            }
        )
        if (isCalculatorDialogOpen) {
            OddsCalculatorDialog(
                Modifier.fillMaxSize(),
                isRounded =  previewState.isRounded,
                roundingMode = previewState.roundingMode,
                onSetAttack = { attack ->
                    previewState = previewState.copy(
                        attack = attack.toString(),
                        odds = com.ica.tabletopassistant.util.calcOdds(
                            attack,
                            previewState.defend.toFloat(),
                            previewState.isRounded,
                            previewState.roundingMode
                        )
                    )
                },
                onSetDefend = { defend ->
                    previewState = previewState.copy(
                        defend = defend.toString(),
                        odds = com.ica.tabletopassistant.util.calcOdds(
                            previewState.attack.toFloat(),
                            defend,
                            previewState.isRounded,
                            previewState.roundingMode
                        )
                    )
                },
                onDismissRequest = {
                    isCalculatorDialogOpen = false
                }
            )
        }

    }
}