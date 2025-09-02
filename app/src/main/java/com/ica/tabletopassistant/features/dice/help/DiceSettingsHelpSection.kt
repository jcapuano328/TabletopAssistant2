package com.ica.tabletopassistant.features.dice.help

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SpaceBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ica.tabletopassistant.features.help.BulletPoint
import com.ica.tabletopassistant.features.help.HelpSection
import com.ica.tabletopassistant.ui.Die

@Composable
fun DiceSettingsHelpSection() {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        //HelpSection(title = "Dice") {
            Column(Modifier.padding(16.dp)) {
                Switch(
                    checked = true,
                    onCheckedChange = { }
                )
                BulletPoint(text = "Toggle the switch to show or hide dice on the tabletop.")
                Spacer(Modifier.height(8.dp))
                BulletPoint(text = "Toggle the check to interpret dice as 1 based.")
                Spacer(Modifier.height(12.dp))

                BulletPoint(text = "Dice are presented in rows on the tabletop.")
                BulletPoint(text = "Up to 5 dice, or spacers, can be shown on a row.")
                BulletPoint(text = "Approximately 5 rows can be shown on the tabletop.")
                BulletPoint(text = "Add dice and spacers to represent the logical groupings of dice on the tabletop.")
                Spacer(Modifier.height(12.dp))

                // add die
                Icon(Icons.Default.Casino, contentDescription = "Add Die")
                BulletPoint(text = "Tap the Add Die button to add a die to the list.")
                BulletPoint(text = "Choose the sides of the die.")
                BulletPoint(text = "Choose the die color from the dropdown.")
                BulletPoint(text = "Choose the dot color from the dropdown.")
                BulletPoint(text = "The preview displays the die with the selected sides and colors")
                Spacer(Modifier.height(4.dp))
                Icon(
                    imageVector = Icons.Default.Delete, // Replace with your asset
                    contentDescription = "Remove Die",
                    tint = MaterialTheme.colorScheme.error
                )
                BulletPoint(text = "Tap the Remove button to remove the spacer from the list.")
                Spacer(Modifier.height(12.dp))

                // add spacer
                Icon(Icons.Default.SpaceBar, contentDescription = "Add Spacer")
                BulletPoint(text = "Tap the Add Spacer button to add a spacer to the list.")
                Spacer(Modifier.height(4.dp))
                Icon(
                    imageVector = Icons.Default.Delete, // Replace with your asset
                    contentDescription = "Remove Die",
                    tint = MaterialTheme.colorScheme.error
                )
                BulletPoint(text = "Tap the Remove button to remove the spacer from the list.")


                Spacer(Modifier.height(16.dp))
            }
        }
    //}
}

@Preview(showBackground = true)
@Composable
fun PreviewDiceSettingsHelpSection() {
    DiceSettingsHelpSection()
}
