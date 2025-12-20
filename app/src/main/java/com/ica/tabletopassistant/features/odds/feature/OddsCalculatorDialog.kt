package com.ica.tabletopassistant.features.odds.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.HelpOutline
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.ica.tabletopassistant.features.calculator.CalculatorDialog
import com.ica.tabletopassistant.features.calculator.CalculatorHelpContent
import com.ica.tabletopassistant.features.calculator.CombatCalculator
import com.ica.tabletopassistant.features.help.HelpDialog
import com.ica.tabletopassistant.ui.InputNumeric
import com.ica.tabletopassistant.ui.PngIcon
import com.ica.tabletopassistant.ui.theme.TabletopAssistantTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OddsCalculatorDialog(
    modifier: Modifier = Modifier,
    onSetAttack: (Float) -> Unit,
    onSetDefend: (Float) -> Unit,
    isRounded: Boolean = false,
    roundingMode: Int = 0,
    onDismissRequest: () -> Unit
) {
    var attack by remember { mutableStateOf(1f)}
    var defend by remember { mutableStateOf(1f)}
    var showHelp by remember { mutableStateOf(false) }

    val odds  = com.ica.tabletopassistant.util.calcOdds(attack, defend, isRounded, roundingMode)

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
                },
                actions = {
                    IconButton(onClick = { showHelp = true }) {
                        Icon(Icons.Default.HelpOutline, contentDescription = "Help")
                    }
                }
            )
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
                Text(
                    text = "Odds",
                    style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp, textAlign = TextAlign.Center),
                    modifier = Modifier.weight(1f)
                )
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
                Text(text = attack.toString(), modifier = Modifier.weight(0.5f), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                Text(text = odds, modifier = Modifier.weight(0.5f), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                Text(text = defend.toString(), modifier = Modifier.weight(0.5f), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            }

            CombatCalculator(
                onAttackCommitted = { value ->
                    attack = value
                    onSetAttack(attack)
                },
                onDefendCommitted = { value ->
                    defend = value
                    onSetDefend(defend)
                }
            )
        }
    }

    if (showHelp) {
        HelpDialog(
            onDismiss = {showHelp = false},
            currentTopic = "Odds Calculator"
        ) {
            CalculatorHelpContent()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewOddsCalculatorDialog() {
    TabletopAssistantTheme {
        OddsCalculatorDialog(
            modifier = Modifier
                .fillMaxSize(),
            isRounded = true,
            roundingMode = 2,
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
