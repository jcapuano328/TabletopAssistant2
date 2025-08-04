package com.ica.tabletopassistant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ica.tabletopassistant.features.SettingsScreen
import com.ica.tabletopassistant.features.TabletopScreen
import com.ica.tabletopassistant.features.calculator.CalculatorDialog
import com.ica.tabletopassistant.features.calculator.CalculatorDialogRequest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    var fabAction by remember { mutableStateOf<suspend () -> Unit>({}) }
    var dialogRequest: CalculatorDialogRequest? by remember { mutableStateOf(null) }
    val openDialog: (Float, (Float) -> Unit, (Float) -> Unit) -> Unit =
        { initial, onSetAttack, onSetDefend ->
            dialogRequest = CalculatorDialogRequest(initial, onSetAttack, onSetDefend)
        }


    Scaffold(
        floatingActionButton = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute == "main") {
                if (dialogRequest == null) {
                    SmallFloatingActionButton(
                        shape = CircleShape,
                        onClick = {
                            // Launch the current screen's FAB action
                            CoroutineScope(Dispatchers.Main).launch {
                                println("MainScreen: FAB clicked")
                                fabAction()
                            }
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.dice_round),
                            contentDescription = "Roll"
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("main") {
                TabletopScreen(
                    onFabClickRequest = { handler -> fabAction = handler },
                    onSettingsClick = { navController.navigate("settings") },
                    openDialog = openDialog
                )
            }
            composable("settings") {
                SettingsScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        dialogRequest?.let { req ->
            CalculatorDialog(
                Modifier.fillMaxSize(),
                onSetAttack = { value ->
                    req.onSetAttack(value)
                },
                onSetDefend = { value ->
                    req.onSetDefend(value)
                },
                onDismissRequest = {
                    dialogRequest = null
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}


