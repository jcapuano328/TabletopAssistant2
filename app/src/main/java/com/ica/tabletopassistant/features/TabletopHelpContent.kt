package com.ica.tabletopassistant.features.general

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ica.tabletopassistant.features.dice.help.DiceHelpSection
import com.ica.tabletopassistant.features.odds.help.OddsHelpSection
import com.ica.tabletopassistant.features.dice.help.RollDiceHelpSection
import com.ica.tabletopassistant.features.spinners.help.SpinnersHelpSection

@Composable
fun TabletopHelpContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxSize()
        .padding(2.dp)) {
        LazyColumn(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
        ) {
            // Section 1
            item {
                OddsHelpSection()
            }

            // Section 2
            item {
                SpinnersHelpSection()
            }

            // Section 3
            item {
                DiceHelpSection()
            }

            // Section 4
            item {
                RollDiceHelpSection()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTabletopHelpContent() {
    TabletopHelpContent(
    )
}
