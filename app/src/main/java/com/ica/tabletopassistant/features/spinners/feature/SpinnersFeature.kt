package com.ica.tabletopassistant.features.spinners.feature

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SpinnersFeature(modifier: Modifier = Modifier, viewModel: SpinnersFeatureViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    SpinnersFeatureContent(
        modifier = modifier,
        state = uiState,
        onUpdateValues = viewModel::updateValues
    )
}

@Composable
fun SpinnersFeatureContent(
    modifier: Modifier = Modifier,
    state: SpinnersFeatureUiState,
    onUpdateValues: (List<Int>) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(2.dp)
    ) {
        /*
        Text("Enabled: ${state.isEnabled}")
        Text("Number: ${state.number}")
        Text("Follow Dice: ${state.followDice}")
        Text("Calc Difference: ${state.calcDifference}")
        Text("Show Calculator: ${state.showCalculator}")
        Text("Values: ${state.values}")
        Text("Difference: ${state.difference}")
        */
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(2.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // let's present a SpinnerItem for each number
            for (i in 0 until state.number) {
                SpinnerItem(
                    modifier = modifier.weight(2f),
                    value = state.values[i],
                    onChanged = {
                        val newValues = state.values.toMutableList()
                        newValues[i] = it
                        onUpdateValues(newValues)
                    }
                )
                if (state.calcDifference && state.number > 1 && i == 0) {
                    // show the difference
                    SpinnerDifference(
                        modifier = modifier.weight(1f),
                        enabled = state.isEnabled,
                        value = state.difference,
                        showCalculator = state.showCalculator,
                        onSetLeft = {
                            val newValues = state.values.toMutableList()
                            newValues[0] = it
                            onUpdateValues(newValues)
                        },
                        onSetRight = {
                            val newValues = state.values.toMutableList()
                            newValues[1] = it
                            onUpdateValues(newValues)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSpinnersFeature() {
    // Use remember to store the state locally within the preview
    var previewState by remember {
        mutableStateOf(
            SpinnersFeatureUiState(
                isEnabled = true,
                number = 2,
                followDice = true,
                calcDifference = true,
                showCalculator = true,
                values = listOf(4, 2),
                difference = 2
            )
        )
    }

    SpinnersFeatureContent(
        state = previewState,
        onUpdateValues = { values ->
            previewState = previewState.copy(values = values)
            if (values.size > 1 && previewState.calcDifference)
                previewState = previewState.copy(difference = values[0] - values[1])
        }
    )
}