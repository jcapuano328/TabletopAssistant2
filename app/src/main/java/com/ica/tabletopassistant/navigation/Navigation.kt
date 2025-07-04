package com.ica.tabletopassistant.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.compose.material3.FabPosition
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.ica.tabletopassistant.R
import com.ica.tabletopassistant.viewmodels.DiceRollViewModel
import com.ica.tabletopassistant.screens.TabletopScreen
import com.ica.tabletopassistant.screens.SettingsScreen
import com.ica.tabletopassistant.ui.PngIcon

// In Navigation.kt

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val diceRollViewModel: DiceRollViewModel = viewModel()
    Scaffold(
        bottomBar = { MainBottomNavigationBar(navController = navController) },
        floatingActionButton = { MainFloatingActionButton(navController, diceRollViewModel) },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        MainNavigationContent(navController, innerPadding, diceRollViewModel)
    }
}

@Composable
fun MainNavigationContent(navController: NavHostController, innerPadding: PaddingValues, viewModel: DiceRollViewModel) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestinations.Tabletop.route,
        modifier = Modifier.padding(innerPadding) // Apply padding here
    ) {
        composable(NavigationDestinations.Tabletop.route) { TabletopScreen(navController, viewModel) }
        composable(NavigationDestinations.Settings.route) { SettingsScreen(navController) }
    }
}

@Composable
fun MainBottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomAppBar(
        actions = {
            MyNavigationItems(navController, currentDestination)
        },
        modifier = Modifier,
        tonalElevation = 10.dp,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    )
}

@Composable
fun RowScope.MyNavigationItems(navController: NavHostController, currentDestination: NavDestination?) {
    listOf(
        NavigationDestinations.Tabletop,
    ).forEach { destination ->
        NavigationBarItem(
            icon = { PngIcon(resId = destination.icon, desc = destination.route) },
            label = { Text(text = destination.route) },
            selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
            onClick = {
                navController.navigate(destination.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.White,
                selectedTextColor = Color.Red,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
    }
    Spacer(Modifier.weight(1f)) // Space for FAB
    listOf(
        NavigationDestinations.Settings
    ).forEach { destination ->
        NavigationBarItem(
            icon = { PngIcon(resId = destination.icon, desc = destination.route) },
            label = { Text(text = destination.route) },
            selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
            onClick = {
                navController.navigate(destination.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.White,
                selectedTextColor = Color.Red,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
    }
}

@Composable
fun MainFloatingActionButton(navController: NavHostController, viewModel: DiceRollViewModel) {
    val scope  = rememberCoroutineScope()
    SmallFloatingActionButton(
        modifier = Modifier.offset(y = 80.dp), // Moves FAB downward into the BottomAppBar
        shape = CircleShape,
        onClick = {
        scope.launch {
            viewModel.onFabClicked()
        }
    }) {
        Image(
            painter = painterResource(id = R.drawable.dice_round),
            contentDescription = "Roll"
        )
    }
}