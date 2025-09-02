package com.ica.tabletopassistant.features.dice.help

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ica.tabletopassistant.R
import com.ica.tabletopassistant.features.help.BulletPoint
import com.ica.tabletopassistant.features.help.HelpSection
import com.ica.tabletopassistant.ui.PngIcon

@Composable
fun RollDiceHelpSection(modifier: Modifier = Modifier) {
    HelpSection(title = "Roll Dice") {
        Card(
            modifier = Modifier
                .padding(1.dp)
                .size(60.dp),
            shape = CircleShape
        ) {
            PngIcon(R.drawable.dice_round, "Dice", modifier = Modifier.size(60.dp))
        }
        BulletPoint(text = "Tap the button to roll the dice.")
        Spacer(Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRollDiceHelpSection() {
    RollDiceHelpSection(
    )
}
