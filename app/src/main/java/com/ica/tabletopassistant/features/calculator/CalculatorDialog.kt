package com.ica.tabletopassistant.features.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ica.tabletopassistant.R
import com.ica.tabletopassistant.ui.theme.TabletopAssistantTheme

data class CalculatorDialogRequest(
    val initialValue: Float,
    val onSetAttack: (Float) -> Unit,
    val onSetDefend: (Float) -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
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
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                //title = { Text("Calculator") },
                title = { },
                navigationIcon = {
                    IconButton(onClick = onDismissRequest) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
                //actions = {
                //    IconButton(onClick = onDismissRequest) {
                //        PngIcon(R.drawable.close_tertiary, "Back")
                //    }
                //}
            )

            CombatCalculator(
                onAttackCommitted = onSetAttack,
                onDefendCommitted = onSetDefend
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorDialog() {
    TabletopAssistantTheme {
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
}
