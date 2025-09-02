package com.ica.tabletopassistant.features.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ica.tabletopassistant.R
import com.ica.tabletopassistant.features.help.BulletPoint
import com.ica.tabletopassistant.features.help.HelpSection
import com.ica.tabletopassistant.ui.PngIcon

@Composable
fun CalculatorHelpContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxSize()
        .padding(2.dp)) {
        Card(modifier = Modifier.padding(2.dp)) {
            Text(
                "This is a simple calculator to help determine strengths and modifiers.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
        ) {
            // Section 1
            item {
                HelpSection(title = "Procedure") {
                    BulletPoint(text = "Use the number pad to calculate values.")
                    BulletPoint(text = "Add final result to the Attack or Defend value by choosing the appropriate button.")
                    Spacer(Modifier.height(16.dp))
                }
            }

            // Section 2
            item {
                HelpSection(title = "ATT, DEF") {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        PngIcon(R.drawable.calc_att, "ATT", modifier = Modifier.size(40.dp))
                        PngIcon(R.drawable.calc_def, "DEF", modifier = Modifier.size(40.dp))
                    }
                    BulletPoint(text = "Apply the value to the Attack or Defend input on the main screen.")
                    BulletPoint(text = "ATT: Apply to Attacker.")
                    BulletPoint(text = "DEF: Apply to Defender.")
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorHelpContent() {
    CalculatorHelpContent(
    )
}
