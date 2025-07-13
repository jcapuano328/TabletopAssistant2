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
import com.ica.tabletopassistant.ui.CalculatorDialog

@Composable
fun OddsFeature(modifier: Modifier = Modifier, viewModel: OddsFeatureViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    OddsFeatureContent(
        modifier = modifier,
        state = uiState,
        onUpdateAttack = viewModel::setAttack,
        onUpdateDefend = viewModel::setDefend,
    )
}

@Composable
fun OddsFeatureContent(
    modifier: Modifier = Modifier,
    state: OddsFeatureUiState,
    onUpdateAttack: (Float) -> Unit = {},
    onUpdateDefend: (Float) -> Unit = {}
) {
    var isCalculatorDialogOpen by remember { mutableStateOf(false) }

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
                        isCalculatorDialogOpen = true
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
                value = state.attack.toString(),
                onValueChange = { newValue ->
                    onUpdateAttack(newValue.toFloatOrNull() ?: 0f)
                },
                label = "Attacker",
                modifier = Modifier.weight(1f)
            )
            Text(text = state.odds, modifier = Modifier.weight(0.5f), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            InputNumeric(
                value = state.defend.toString(),
                onValueChange = { newValue ->
                    onUpdateDefend(newValue.toFloatOrNull() ?: 0f)
                },
                label = "Defender",
                modifier = Modifier.weight(1f)
            )
        }
    }

    if (isCalculatorDialogOpen) {
        CalculatorDialog(
            Modifier.fillMaxSize(),
            onSetAttack = { value ->
                onUpdateAttack(value)
            },
            onSetDefend = { value ->
                onUpdateDefend(value)
            },
            onDismissRequest = {
                isCalculatorDialogOpen = false
            }
        )
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
                attack = 13.5f,
                defend = 4.36f
            )
        )
    }

    previewState = previewState.copy(odds = com.ica.tabletopassistant.util.MathUtils().calcOdds(previewState.attack, previewState.defend, previewState.isRounded, previewState.roundingMode))

    OddsFeatureContent(
        state = previewState,
        onUpdateAttack = { attack ->
            previewState = previewState.copy(
                attack = attack,
                odds = com.ica.tabletopassistant.util.MathUtils().calcOdds(attack, previewState.defend, previewState.isRounded, previewState.roundingMode)
            )
        },
        onUpdateDefend = { defend ->
            previewState = previewState.copy(
                defend = defend,
                odds = com.ica.tabletopassistant.util.MathUtils().calcOdds(previewState.attack, defend, previewState.isRounded, previewState.roundingMode)
            )
        }
    )
}