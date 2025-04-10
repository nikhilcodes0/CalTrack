package com.example.calorietracker.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.calorietracker.ui.theme.poppinsFontFamily

data class LoggedMeal(
    val name: String = "",
    val calories: Int = 0,
    val timestamp: Long = 0L,
    val mealType: String = "", // e.g. "Breakfast", "Lunch", "Dinner", "Snack"
    val mealTime: String = "",   // e.g. "2:00 PM"
    val grams: Int = 0
)
fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}


@Composable
fun HistoryScreen(

) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val meals = remember { mutableStateListOf<LoggedMeal>() }
    val openDialog = remember { mutableStateOf(false) }
    val selectedMeal = remember { mutableStateOf<LoggedMeal?>(null) }

    LaunchedEffect(Unit) {
        if (userId != null) {
            FirebaseFirestore.getInstance()
                .collection("users").document(userId)
                .collection("logged_meals")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->
                    meals.clear()
                    for (doc in result) {
                        val meal = doc.toObject(LoggedMeal::class.java)
                        meals.add(meal)
                    }
                }
        }
    }

    Column(
        modifier = Modifier
            .background(Color(0xFF2E3440))
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = "Meals History",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Black,
                color = Color.White,
                fontSize = 35.sp,
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)

        ) {
            meals.groupBy { formatDate(it.timestamp) }.forEach { (date, mealsOnDate) ->
                item {
                    Text(
                        text = date,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }

                val mealsByType = mealsOnDate.groupBy { it.mealType }

                mealsByType.forEach { (type, mealsOfType) ->
                    item {
                        Text(
                            text = type,
                            fontSize = 18.sp,
                            color = Color(0xFF88C0D0),
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )
                    }

                    items(mealsOfType) { meal ->
                        Box(
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF4C566A))
                                .padding(16.dp)
                                .clickable {
                                    selectedMeal.value = meal
                                    openDialog.value = true
                                }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = meal.name, color = Color.White, fontSize = 16.sp)

                                Text(text = meal.mealTime, color = Color.White, fontSize = 16.sp)
                                Text(
                                    text = "${meal.calories} kcal",
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
        if (openDialog.value && selectedMeal.value != null) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { openDialog.value = false },
                confirmButton = {
                    Text(
                        text = "Close",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { openDialog.value = false },
                        color = Color(0xFF88C0D0)
                    )
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Meal Details", color = Color.White, modifier = Modifier.padding(bottom = 14.dp), fontFamily = poppinsFontFamily)
                    }
                },

                        text = {
                    Column {
//                        Text("Name: ${selectedMeal.value!!.name}", color = Color.White)
//                        Text("Type: ${selectedMeal.value!!.mealTime}", color = Color.White)
//                        Text("Calories: ${selectedMeal.value!!.calories} kcal", color = Color.White)
//                        Text("Amount: ${selectedMeal.value!!.grams} grams", color = Color.White)
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                                        .background(Color(0xFF93A9FD)) // Same background as TextField
                                        .padding(10.dp, 5.dp),// Padding inside the shape

                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Name",
                                        fontFamily = poppinsFontFamily,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }

                                Text(
                                    text = selectedMeal.value!!.name,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                                        .background(Color(0xFF93A9FD)) // Same background as TextField
                                        .padding(10.dp, 5.dp),// Padding inside the shape

                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Calories",
                                        fontFamily = poppinsFontFamily,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }

                                Text(
                                    text = "${selectedMeal.value!!.calories} kCal",
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                        }

                        Row (
                            modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                                        .background(Color(0xFF93A9FD)) // Same background as TextField
                                        .padding(10.dp, 5.dp),// Padding inside the shape

                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Meal Time",
                                        fontFamily = poppinsFontFamily,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }

                                Text(
                                    text = selectedMeal.value!!.mealTime,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                                        .background(Color(0xFF93A9FD)) // Same background as TextField
                                        .padding(10.dp, 5.dp),// Padding inside the shape

                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Amount(g)",
                                        fontFamily = poppinsFontFamily,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }

                                Text(
                                    text = "${selectedMeal.value!!.grams}g",
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )
                            }
                        }

                        Column (
                            modifier = Modifier.padding(top = 30.dp).fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                                    .background(Color(0xFF93A9FD)) // Same background as TextField
                                    .padding(10.dp, 5.dp),// Padding inside the shape

                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Time",
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }

                            Text(
                                text = formatDate(selectedMeal.value!!.timestamp),
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 5.dp)
                            )
                        }

                    }
                },
                containerColor = Color(0xFF3B4252),
                titleContentColor = Color.White,
                textContentColor = Color.White
            )
        }

    }

}


@Preview
@Composable
fun HistoryScreenPreview() {
    HistoryScreen()
}

