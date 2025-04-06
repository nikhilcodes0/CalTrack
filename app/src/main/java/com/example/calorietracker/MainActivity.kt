package com.example.calorietracker




import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color


import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calorietracker.ui.theme.CalorieTrackerTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calorietracker.screens.HomePage
import com.example.calorietracker.screens.LoginScreen
import com.example.calorietracker.screens.Meals
import com.example.calorietracker.screens.MoreInfoScreen
import com.example.calorietracker.screens.PredefinedMeals
import com.example.calorietracker.screens.ProfileScreen
import com.example.calorietracker.screens.SignUpScreen
import com.example.calorietracker.ui.theme.poppinsFontFamily
import com.google.firebase.FirebaseApp


const val ROUTE_WELCOME = "welcome"
const val ROUTE_SIGNUP = "signup"
const val ROUTE_LOGIN = "login"
const val ROUTE_INFO = "info"
const val ROUTE_PROFILE = "profile"
const val ROUTE_HOME = "home"
const val ROUTE_MEALS = "meals"
const val ROUTE_PREDEFINED = "predefined"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        val startDestination = if (currentUser != null) {
            "$ROUTE_HOME/${currentUser.uid}"
        } else {
            ROUTE_WELCOME
        }
        enableEdgeToEdge()
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   NavHost(
                       navController = navController,
                       startDestination = startDestination,
                       modifier = Modifier.padding(innerPadding),
                       builder = {
                           composable(ROUTE_SIGNUP) {
                               SignUpScreen(navController)
                           }

                           composable(ROUTE_WELCOME) {
                               WelcomeScreen(innerPadding, navController)
                           }

                           composable(ROUTE_LOGIN) {
                               LoginScreen(navController)
                           }
                           composable("$ROUTE_INFO/{userId}") { backStackEntry ->
                               val userId = backStackEntry.arguments?.getString("userId") ?: ""
                               MoreInfoScreen(navController, userId)
                           }
                           composable("$ROUTE_PROFILE/{userId}") { backStackEntry ->
                               val userId = backStackEntry.arguments?.getString("userId") ?: ""
                               ProfileScreen(navController, userId)
                           }
                           composable("$ROUTE_HOME/{userId}") {
                               backStackEntry ->
                               val userId = backStackEntry.arguments?.getString("userId") ?: ""
                               HomePage(navController, userId)
                           }
                           composable("$ROUTE_MEALS/{userId}"){
                               backStackEntry ->
                               val userId = backStackEntry.arguments?.getString("userId") ?: ""
                               Meals(navController, userId)
                           }

                           composable(ROUTE_PREDEFINED) {
                               PredefinedMeals()
                           }

                       }
                   )
                }
            }
        }
    }
}



@Composable
fun WelcomeScreen(paddingValues: PaddingValues,navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E3440))
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // Ensures top and bottom spacing
    ) {
        Spacer(modifier = Modifier.weight(1f)) // Push content to center

        Column( // Centered content
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome To",
                color = Color.White,
                fontSize = 50.sp,
                fontFamily = poppinsFontFamily
            )
            Text(
                text = "CoreBurn",
                color = Color.White,
                fontSize = 50.sp,
                fontFamily = poppinsFontFamily
            )

            Text(
                text = "Count your Calories",
                color = Color.Gray,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Push button to bottom

        Button(
            onClick = {
                println("Button Clicked")
                navController.navigate(ROUTE_SIGNUP){
                    launchSingleTop = true
                } },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4C566A)
            ),
            modifier = Modifier
                .fillMaxWidth()

                .padding(bottom = 60.dp)

                .padding(horizontal = 25.dp)

        ) {
            Text(text = "Get Started", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = poppinsFontFamily)
        }
    }
}














@Preview
@Composable
fun WelcomeScreenPreview() {
    val fakeNavController = rememberNavController()
    WelcomeScreen(paddingValues = PaddingValues(0.dp), fakeNavController)
}