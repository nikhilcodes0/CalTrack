package com.example.calorietracker.screens

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.calorietracker.ROUTE_HOME
import com.example.calorietracker.ROUTE_MEALS
import com.example.calorietracker.ROUTE_PROFILE
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.getValue
import com.example.calorietracker.ROUTE_HISTORY


@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Meals,
        BottomNavItem.Profile,
        BottomNavItem.History
    )

    NavigationBar(
        containerColor = Color(0xFF2E3440),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute?.startsWith(item.route) == true,
                onClick = {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    val fullRoute = when (item) {
                        is BottomNavItem.Home -> "$ROUTE_HOME/$userId"
                        is BottomNavItem.Profile -> "$ROUTE_PROFILE/$userId"
                        is BottomNavItem.Meals -> "$ROUTE_MEALS/$userId"
                        is BottomNavItem.History -> ROUTE_HISTORY
                    }
                    navController.navigate(fullRoute) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
