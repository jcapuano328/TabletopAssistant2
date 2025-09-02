package com.ica.tabletopassistant.features.spinners.help

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun SpinnersSettingsHelpSection() {
    //HelpSection(title = "Spinners") {
        Column(Modifier.padding(16.dp)) {
            Switch(
                checked = true,
                onCheckedChange = { }
            )
            BulletPoint(text = "Toggle the switch to show or hide spinners on the tabletop.")
            Spacer(Modifier.height(12.dp))

            BulletPoint(text = "Adjust the Slider to choose the number of spinners.")
            Spacer(Modifier.height(4.dp))
            BulletPoint(text = "Toggle the check for the various options:")
            Column(Modifier.padding(start = 16.dp)) {
                BulletPoint(text = "Follow Dice: Set the value to the dice result.")
                BulletPoint(text = "Difference: Display the difference between the first 2 spinner values.")
                BulletPoint(text = "Calculator: present the calculator button.")
            }
            Spacer(Modifier.height(16.dp))
        }
    //}
}

@Preview(showBackground = true)
@Composable
fun PreviewSpinnersSettingsHelpSection() {
    SpinnersSettingsHelpSection()
}
