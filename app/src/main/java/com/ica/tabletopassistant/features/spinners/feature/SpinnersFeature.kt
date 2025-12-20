package com.ica.tabletopassistant.features.spinners.feature

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ica.tabletopassistant.features.calculator.CalculatorDialog
import com.ica.tabletopassistant.features.calculator.CalculatorDialogRequest
import com.ica.tabletopassistant.features.calculator.CalculatorDialogType

@Composable
fun SpinnersFeature(
    modifier: Modifier = Modifier,
    viewModel: SpinnersFeatureViewModel = hiltViewModel(),
    showDialog: (type: CalculatorDialogType, isRounded: Boolean, roundingMode: Int, initial: Float, onSetAttack: (Float) -> Unit, onSetDefend: (Float) -> Unit) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    SpinnersFeatureContent(
        modifier = modifier,
        state = uiState,
        onUpdateValues = { values ->
            viewModel.updateValues(values)
        },
        onUpdateValueAt = { index, value ->
            viewModel.updateValueAt(index, value)
        },
        showDialog = showDialog
    )
}

@Composable
fun SpinnersFeatureContent(
    modifier: Modifier = Modifier,
    state: SpinnersFeatureUiState,
    onUpdateValueAt: (Int, Int) -> Unit = { _, _ -> },
    onUpdateValues: (List<Int>) -> Unit = {},
    showDialog: (type: CalculatorDialogType, isRounded: Boolean, roundingMode: Int, initial: Float, onSetAttack: (Float) -> Unit, onSetDefend: (Float) -> Unit) -> Unit
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
            if (state.calcDifference && state.number > 1) {
                // show the difference
                SpinnerDifference(
                    modifier = modifier.weight(1f),
                    enabled = state.isEnabled,
                    value = state.difference,
                    showCalculator = state.showCalculator,
                    onShowCalculator = {
                        showDialog(
                            CalculatorDialogType.Standard,
                            false,
                            0,
                            0f,//state.values[0].toFloat(),
                            {
                                onUpdateValueAt(0, it.toInt())
                            },
                            {
                                onUpdateValueAt(1, it.toInt())
                            }
                        )
                    }
                )
            }
        }
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

    var dialogRequest: CalculatorDialogRequest? by remember { mutableStateOf(null) }
    val openDialog: (CalculatorDialogType, Boolean, Int,Float, (Float) -> Unit, (Float) -> Unit) -> Unit =
        { type, isRounded, roundingMode,initial, onSetAttack, onSetDefend ->
            dialogRequest = CalculatorDialogRequest(type, isRounded, roundingMode, initial, onSetAttack, onSetDefend)
        }

    SpinnersFeatureContent(
        state = previewState,
        onUpdateValueAt = { index, value ->
            println("PreviewSpinnersFeature onUpdateValueAt: $index $value")
            val newValues = previewState.values.toMutableList()
            println("PreviewSpinnersFeature onUpdateValueAt oldValues: $newValues")
            newValues[index] = value
            println("PreviewSpinnersFeature onUpdateValueAt newValues: $newValues")


            println("PreviewSpinnersFeature onUpdateValues current: ${previewState.values}")
            previewState = previewState.copy(values = newValues)
            if (newValues.size > 1 && previewState.calcDifference)
                previewState = previewState.copy(difference = newValues[0] - newValues[1])
            println("PreviewSpinnersFeature onUpdateValues updated: ${previewState.values}")

        },

        onUpdateValues = { values ->
            println("PreviewSpinnersFeature onUpdateValues: $values")
            println("PreviewSpinnersFeature onUpdateValues current: ${previewState.values}")
            previewState = previewState.copy(values = values)
            if (values.size > 1 && previewState.calcDifference)
                previewState = previewState.copy(difference = values[0] - values[1])
            println("PreviewSpinnersFeature onUpdateValues updated: ${previewState.values}")
        },
        showDialog = openDialog
    )

    dialogRequest?.let { req ->
        CalculatorDialog(
            Modifier.fillMaxSize(),
            onSetAttack = { value ->
                println("CalculatorDialog onSetAttack: $value, current: ${previewState.values}")
                req.onSetAttack(value)
            },
            onSetDefend = { value ->
                println("CalculatorDialog onSetDefend: $value, current: ${previewState.values}")
                req.onSetDefend(value)
            },
            onDismissRequest = {
                dialogRequest = null
            }
        )
    }

}