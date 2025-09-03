package com.ica.tabletopassistant.features.general

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SpaceBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ica.tabletopassistant.features.dice.help.DiceSettingsHelpSection
import com.ica.tabletopassistant.features.help.BulletPoint
import com.ica.tabletopassistant.features.odds.help.OddsSettingsHelpSection
import com.ica.tabletopassistant.features.spinners.help.SpinnersSettingsHelpSection

@Composable
fun SettingsHelpContent() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Dice", "Odds", "Spinners")

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxSize()
        .padding(2.dp)) {

        SettingsHelpSection()

        TabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title, fontSize = 12.sp) }
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            item {
                when (selectedTabIndex) {
                    0 -> DiceSettingsHelpSection()
                    1 -> OddsSettingsHelpSection()
                    2 -> SpinnersSettingsHelpSection()
                }
            }
        }
    }
}

@Composable
fun SettingsHelpSection() {
    Column(Modifier.padding(16.dp)) {
        Card(modifier = Modifier.padding(2.dp)) {
            Text(
                "The tabletop can be configured to present up to 30 6, 8 or 10 sided dice. The dice can be grouped and arrayed in a logical fashion that is appropriate for the game. An optional odds calculator and spinners (counters) can also be added.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
        Icon(Icons.Default.Refresh, contentDescription = "Reset")
        BulletPoint(text = "Tap the Reset button to reset to defaults.")
        Spacer(Modifier.height(4.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsHelpContent() {
    SettingsHelpContent(
    )
}
