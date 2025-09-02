package com.ica.tabletopassistant.features.dice.help

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ica.tabletopassistant.features.help.BulletPoint
import com.ica.tabletopassistant.features.help.HelpSection
import com.ica.tabletopassistant.ui.Die

@Composable
fun DiceHelpSection() {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        // Section 1
        HelpSection(title = "Dice") {
            Column(Modifier.padding(16.dp)) {
                // dice
                Row() {
                    Die(dieNumber = 1,
                        modifier = Modifier
                            .padding(1.dp)
                            .size(40.dp)
                        ,
                        onDieClicked = { },
                        sides = 6,
                        dieColor = Color.White,
                        dotColor = Color.Black,
                        dieValue = 1
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Die(dieNumber = 2,
                        modifier = Modifier
                            .padding(1.dp)
                            .size(40.dp)
                        ,
                        onDieClicked = { },
                        sides = 6,
                        dieColor = Color.Red,
                        dotColor = Color.White,
                        dieValue = 1
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Die(dieNumber = 3,
                        modifier = Modifier
                            .padding(1.dp)
                            .size(40.dp)
                        ,
                        onDieClicked = { },
                        sides = 8,
                        dieColor = Color.Black,
                        dotColor = Color.White,
                        dieValue = 1
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Die(dieNumber = 4,
                        modifier = Modifier
                            .padding(1.dp)
                            .size(40.dp)
                        ,
                        onDieClicked = { },
                        sides = 10,
                        dieColor = Color.Blue,
                        dotColor = Color.White,
                        dieValue = 1
                    )
                }
                Spacer(Modifier.height(8.dp))
                BulletPoint(text = "There are five slots on a row.")
                BulletPoint(text = "Each slot can hold a single die OR a spacer.")
                BulletPoint(text = "Each die is presented in order of the configuration.")
                BulletPoint(text = "Tap a die to increase its value by 1.")
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDiceHelpSection() {
    DiceHelpSection()
}
