package com.ica.tabletopassistant.features.odds.help

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SpaceBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ica.tabletopassistant.R
import com.ica.tabletopassistant.features.help.BulletPoint
import com.ica.tabletopassistant.features.help.HelpSection
import com.ica.tabletopassistant.ui.PngIcon

@Composable
fun OddsSettingsHelpSection() {
    //HelpSection(title = "Odds") {
        Column(Modifier.padding(16.dp)) {
            Switch(
                checked = true,
                onCheckedChange = { }
            )
            BulletPoint(text = "Toggle the switch to show or hide odds on the tabletop.")
            Spacer(Modifier.height(12.dp))

            BulletPoint(text = "Toggle the check to round the odds.")
            Spacer(Modifier.height(4.dp))
            BulletPoint(text = "Choose the rounding mode:")
            Column(Modifier.padding(start = 16.dp)) {
                BulletPoint(text = "Up: round up to nearest whole number.")
                BulletPoint(text = "Std: round to nearest half.")
                BulletPoint(text = "Down: round down to nearest whole number.")
            }
            Spacer(Modifier.height(16.dp))
        }
    //}
}

@Preview(showBackground = true)
@Composable
fun PreviewOddsSettingsHelpSection() {
    OddsSettingsHelpSection()
}
