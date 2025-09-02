package com.ica.tabletopassistant.features.odds.help

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ica.tabletopassistant.R
import com.ica.tabletopassistant.features.help.BulletPoint
import com.ica.tabletopassistant.features.help.HelpSection
import com.ica.tabletopassistant.ui.PngIcon

@Composable
fun OddsHelpSection() {
    HelpSection(title = "Odds") {
        Column(Modifier.padding(16.dp)) {
            BulletPoint(text = "Enter the attacker and defender strength values using the number pad.")
            BulletPoint(text = "The odds are calculated and displayed as the values are modified.")
            PngIcon(R.drawable.calc, "Calculator", modifier = Modifier.size(30.dp))
            BulletPoint(text = "Tap the calculator button to open the calculator.")
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOddsHelpSection() {
    OddsHelpSection()
}
