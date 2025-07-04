package com.ica.tabletopassistant.navigation

import com.ica.tabletopassistant.ui.PngIcons


sealed class NavigationDestinations(val route: String, val icon: Int) {
    object Tabletop : NavigationDestinations("Table", PngIcons.TABLE)
    object Settings : NavigationDestinations("Settings", PngIcons.SETTINGS)
}