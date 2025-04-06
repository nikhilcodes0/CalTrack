package com.example.calorietracker.screens

import androidx.compose.foundation.Image
import com.example.calorietracker.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calorietracker.ROUTE_PREDEFINED
import com.example.calorietracker.ui.theme.poppinsFontFamily

@Composable
fun Meals(navController: NavController, userId: String) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E3440))
            .padding(20.dp),
    ) {
        Text(
            text = "Choose a Option:",
            color = Color.White,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 25.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 30.dp)

        )

        Spacer(modifier = Modifier.height(35.dp))

        Box (
            modifier = Modifier
                .clickable {
                    navController.navigate(ROUTE_PREDEFINED)
                }
                .clip(RoundedCornerShape(10.dp))
                .width(250.dp)
                .height(150.dp)
                .background(Color(0xFF4C566A))
                .align(Alignment.CenterHorizontally),

        ) {
            Column(modifier = Modifier.fillMaxSize(),  horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.menu_book),
                    contentDescription = "Predefined Meals",
                    modifier = Modifier
                        .width(80.dp)
                        .scale(1.5f)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                )

                Text(
                    text = "Predefined Meals",
                    color = Color.White,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 30.dp)
                )
            }
        }


        Spacer(modifier = Modifier.height(35.dp))

        Box (
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .width(250.dp)
                .height(150.dp)
                .background(Color(0xFF4C566A))
                .align(Alignment.CenterHorizontally),
        ) {
            Column(modifier = Modifier.fillMaxSize(),  horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.skillet),
                    contentDescription = "Add a new Meal",
                    modifier = Modifier
                        .width(80.dp)
                        .scale(1.5f)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                )

                Text(
                    text = "Your Meals",
                    color = Color.White,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 30.dp)
                )
            }
        }
    }
}

