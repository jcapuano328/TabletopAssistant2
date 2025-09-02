package com.ica.tabletopassistant.features.spinners.help

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
fun SpinnersHelpSection() {
    HelpSection(title = "Spinners") {
        Column(Modifier.padding(16.dp)) {
            BulletPoint(text = "Enter the value using the number pad.")
            BulletPoint(text = "Tap the the Plus or Minus buttons to increment or decrement the value by 1.")
            BulletPoint(text = "Tap the Reset button to set the value to 0.")
            BulletPoint(text = "The difference between the first 2 spinners is calculated and displayed when configured.")
            PngIcon(R.drawable.calc, "Calculator", modifier = Modifier.size(30.dp))
            BulletPoint(text = "Tap the calculator button to open the calculator.")
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSpinnersHelpSection() {
    SpinnersHelpSection()
}
