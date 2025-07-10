package com.ica.tabletopassistant.features

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.ica.tabletopassistant.ui.PngIcon
import com.ica.tabletopassistant.viewmodels.DiceRollViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabletopScreen(onFabClickRequest: (suspend () -> Unit) -> Unit = {},
                   onSettingsClick: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val diceRollViewModel: DiceRollViewModel = viewModel()

    LaunchedEffect(Unit) {
        onFabClickRequest {
            // This code runs when the FAB is clicked
            diceRollViewModel.fabEvent.collect {
                println("FAB tapped in TabletopScreen!")
            }
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(2.dp)) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onSecondary
            ),
            title = { Text("Tabletop Assistant") },
            actions = {
                IconButton(onClick = onSettingsClick) {
                    //Icon(Icons.Default.Settings, contentDescription = "Settings")
                    PngIcon(
                        resId = com.ica.tabletopassistant.R.drawable.settings_tertiary,
                        desc = "Settings",
                        modifier = Modifier
                            .size(32.dp)
                            //.align(Alignment.Center)
                    )

                }
            }
        )
        Text("Tabletop Screen")
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTabletopScreen() {
    TabletopScreen(
    )
}


