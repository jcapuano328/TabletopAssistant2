package com.ica.tabletopassistant.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ica.tabletopassistant.R
import com.ica.tabletopassistant.features.help.BulletPoint
import com.ica.tabletopassistant.features.help.HelpSection
import com.ica.tabletopassistant.ui.PngIcon

@Composable
fun TabletopAssistantAboutContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxSize()
        .padding(2.dp)) {
        LazyColumn(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            // Section 1
            item {
                TabletopAssistantIntroductionSection()
            }
            // Section 2
            item {
                TabletopAssistantGeneralSection()
            }
            /*
            // Section 3
            item {
                TabletopAssistantMeleeResolutionSection()
            }
            // Section 4
            item {
                TabletopAssistantMoraleResolutionSection()
            }
            // Section 5
            item {
                TabletopAssistantGeneralSection()
            }
            */
        }
    }
}

@Composable
fun TabletopAssistantIntroductionSection() {
    HelpSection(title = "Introduction") {
        Column(Modifier.padding(16.dp)) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape) // Clips the Box to a circle
                    .background(MaterialTheme.colorScheme.primary), // Sets the background color
                contentAlignment = Alignment.Center // Centers the icon within the Box
            ) {
                PngIcon(R.drawable.table, "LOGO", modifier = Modifier.size(40.dp))
            }

            Card(modifier = Modifier.padding(2.dp)) {
                Text(
                    "This is a simple dice roller assistant for tabletop games. While the primary purpose is to act as a dice roller, an odds ratio calculator and simple counters (spinners) are also provided (because many games revolve around odds and modifiers).",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    "The tabletop can be configured to present up to 30 6, 8 or 10 sided dice. The dice can be grouped and arrayed in a logical fashion that is appropriate for the game.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Icon(Icons.Default.Settings, contentDescription = "Settings")
            BulletPoint(text = "Tap the settings button to configure the tabletop.")
            Spacer(Modifier.height(16.dp))

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun TabletopAssistantGeneralSection() {
    HelpSection(title = "Navigation") {
        Column(Modifier.padding(16.dp)) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
            BulletPoint(text = "Tap the settings button to configure the tabletop.")
            Spacer(Modifier.height(16.dp))

            Icon(Icons.Default.HelpOutline, contentDescription = "Help")
            BulletPoint(text = "Tap the help button to get additional information for the screen.")
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTabletopAssistantAboutContent() {
    TabletopAssistantAboutContent(
    )
}
