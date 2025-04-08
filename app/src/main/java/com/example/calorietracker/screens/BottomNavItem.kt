package com.example.calorietracker.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Filled.Home)
    object Profile : BottomNavItem("profile", "Profile", Icons.Filled.Person)
    object Meals : BottomNavItem("meals", "Meals", Icons.Default.Restaurant)
}
