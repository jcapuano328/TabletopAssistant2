package com.ica.tabletopassistant.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorDialog(
    modifier: Modifier = Modifier,
    onSetAttack: (Float) -> Unit,
    onSetDefend: (Float) -> Unit,
    onDismissRequest: () -> Unit
) {
    Card(modifier = modifier.padding(2.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = androidx.compose.ui.Alignment.Start
        ) {
            //ProportionalStrengthCalculator(
            //    modifier = modifier.weight(10f),
            //    onSetAttack = onSetAttack,
            //    onSetDefend = onSetDefend
            //)
            Button(modifier = Modifier.weight(1f), onClick = onDismissRequest) {
                Text("Close")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorDialog() {
    CalculatorDialog(
        modifier = Modifier
            .fillMaxSize(),
        onSetAttack = { value ->
            println("Attack value: $value")
        },
        onSetDefend = { value ->
            println("Defend value: $value")
        },
        onDismissRequest = {
            println("Dialog dismissed")
        }
    )
}
