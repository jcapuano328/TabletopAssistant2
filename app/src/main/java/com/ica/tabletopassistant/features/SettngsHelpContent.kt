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
    val tabTitles = listOf("General", "Dice", "Odds", "Spins")

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxSize()
        .padding(2.dp)) {

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
                    0 -> SettingsHelpSection()
                    1 -> DiceSettingsHelpSection()
                    2 -> OddsSettingsHelpSection()
                    3 -> SpinnersSettingsHelpSection()
                }
            }
        }


        /*
        LazyColumn(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
        ) {
            // Section 1
            item {
                DiceSettingsHelpSection()
            }

            // Section 2
            item {
                OddsSettingsHelpSection()
            }

            // Section 3
            item {
                SpinnersSettingsHelpSection()
            }
        }
        */
    }
}

@Composable
fun SettingsHelpSection() {
    Column(Modifier.padding(16.dp)) {
        Icon(Icons.Default.Refresh, contentDescription = "Reset")
        BulletPoint(text = "Tap the Reset button to reset to defaults.")
        Spacer(Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsHelpContent() {
    SettingsHelpContent(
    )
}
