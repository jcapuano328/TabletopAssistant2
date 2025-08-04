package com.ica.tabletopassistant.features

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ica.tabletopassistant.R
import com.ica.tabletopassistant.features.dice.feature.DiceFeature
import com.ica.tabletopassistant.features.odds.feature.OddsFeature
import com.ica.tabletopassistant.features.spinners.feature.SpinnersFeature
import com.ica.tabletopassistant.features.calculator.CalculatorDialog
import com.ica.tabletopassistant.features.calculator.CalculatorDialogRequest

import com.ica.tabletopassistant.ui.PngIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabletopScreen(onFabClickRequest: (suspend () -> Unit) -> Unit = {},
                   onSettingsClick: () -> Unit = {},
                   openDialog: (initial: Float, onSetAttack: (Float) -> Unit, onSetDefend: (Float) -> Unit) -> Unit,
                   viewModel: TabletopScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()/*.padding(2.dp)*/) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onSecondary
            ),
            navigationIcon = {
                // perhaps make this a button and display the "about" popup?
                PngIcon(
                    resId = com.ica.tabletopassistant.R.drawable.table,
                    desc = "Logo",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(40.dp)
                    //.align(Alignment.Center)
                )
            },
            title = { Text("Tabletop Assistant") },
            actions = {
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                    /*
                    PngIcon(
                        resId = com.ica.tabletopassistant.R.drawable.settings_tertiary,
                        desc = "Settings",
                        modifier = Modifier
                            .size(32.dp)
                            //.align(Alignment.Center)
                    )
                    */
                }
            }
        )
        /*
        Text("Tabletop Screen")
        Text("Odds Enabled: ${uiState.isOddsEnabled}")
        Text("Spinners Enabled: ${uiState.isSpinnersEnabled}")
        Text("Dice Enabled: ${uiState.isDiceEnabled}")
        */

        if (uiState.isOddsEnabled) {
            OddsFeature(showDialog = openDialog)
        }
        if (uiState.isSpinnersEnabled) {
            SpinnersFeature(showDialog = openDialog)
        }
        if (uiState.isDiceEnabled) {
            DiceFeature(onFabClickRequest = onFabClickRequest)
        }
    }
}


