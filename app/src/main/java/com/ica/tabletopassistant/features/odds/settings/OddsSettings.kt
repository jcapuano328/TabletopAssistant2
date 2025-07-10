package com.ica.tabletopassistant.features.odds.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun OddsSettings(modifier: Modifier = Modifier, viewModel: OddsSettingsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    OddsSettingsContent(
        modifier = modifier,
        state = uiState,
        onToggleIsEnabled = viewModel::setIsEnabled,
        onToggleIsRounded = viewModel::setIsRounded,
        onUpdateRoundingMode = viewModel::setRoundingMode
    )

}

@Composable
fun OddsSettingsContent(
    modifier: Modifier = Modifier,
    state: OddsSettingsUiState,
    onToggleIsEnabled: (Boolean) -> Unit,
    onToggleIsRounded: (Boolean) -> Unit,
    onUpdateRoundingMode: (Int) -> Unit
) {
    val modeOptions = listOf(
        ModeOption( value = 0, description = "Up"),
        ModeOption( value = 1, description = "Std"),
        ModeOption( value = 2, description = "Down")
    )
    val selectedMode = modeOptions.find { it.value == state.roundingMode }
        ?: modeOptions.firstOrNull()

    Column(
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
                text = "Odds",
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp, textAlign = TextAlign.Center)
            )
        }

        // -- FLAGS --
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                //.padding(4.dp)
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

            // -- ROUNDED --
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.weight(1f)
            ) {
                /*Switch(
                    checked = state.isRounded,
                    onCheckedChange = onToggleIsRounded
                )*/
                Checkbox(
                    checked = state.isRounded,
                    onCheckedChange = onToggleIsRounded,
                    enabled = state.isEnabled
                )
                Text("Round")
            }

            // --- ROUNDING MODE SELECTOR (Segmented Buttons) ---
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(2f)
            ) {
                modeOptions.forEach { option ->
                    val isSelected = option == selectedMode
                    OutlinedButton(
                        onClick = {
                            onUpdateRoundingMode(option.value)
                        },
                        enabled = state.isEnabled,
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                            contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            //.size(32.dp)
                            .weight(1f)
                            .padding(horizontal = 2.dp)
                    ) {
                        Text(text = option.description)
                    }
                }
            }

        }
    }
}

data class ModeOption(
    val value: Int,
    val description: String
)

/*
        if (state.isEnabled) {
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = config.attack.toString(),
                onValueChange = {
                    it.toFloatOrNull()?.let(viewModel::setAttack)
                },
                label = { Text("Attack Value") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = config.defend.toString(),
                onValueChange = {
                    it.toFloatOrNull()?.let(viewModel::setDefend)
                },
                label = { Text("Defend Value") },
                modifier = Modifier.fillMaxWidth()
            )
        }
 */

@Preview(showBackground = true)
@Composable
fun PreviewOddsSettings() {
    // Use remember to store the state locally within the preview
    var previewState by remember {
        mutableStateOf(
            OddsSettingsUiState(
                isEnabled = true,
                isRounded = false,
                roundingMode = 0,
                attack = 1f,
                defend = 1f

            )
        )
    }

    OddsSettingsContent(
        state = previewState,
        onToggleIsEnabled = { isEnabled ->
            previewState = previewState.copy(isEnabled = isEnabled)
        },
        onToggleIsRounded = { isRounded ->
            previewState = previewState.copy(isRounded = isRounded)
        },
        onUpdateRoundingMode = { mode ->
            previewState = previewState.copy(roundingMode = mode)
        }
    )
}