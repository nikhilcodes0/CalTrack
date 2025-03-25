package com.example.calorietracker.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.calorietracker.ui.theme.poppinsFontFamily


@Composable
fun ProfileScreen() {
    Text(text = "Profile Page", fontFamily = poppinsFontFamily)
}


@Preview
@Composable
fun ProfilePreview() {
    ProfileScreen()
}