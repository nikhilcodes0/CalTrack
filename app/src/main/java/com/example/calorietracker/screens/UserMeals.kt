package com.example.calorietracker.screens

import android.R.attr.onClick
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import com.example.calorietracker.ui.theme.poppinsFontFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore




data class UserMeal(
    val name: String = "",
    val calories: Int = 0,
)


@Composable
fun UserMealsList(
    onMealSelected: (UserMeal) -> Unit
) {
    val usermeals = remember { mutableStateListOf<UserMeal>() }
    val context = LocalContext.current
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(Unit) {
        if (userId != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(userId)
                .collection("user_meals")
                .get()
                .addOnSuccessListener { result ->
                    usermeals.clear()
                    for (document in result) {
                        val meal = document.toObject(UserMeal::class.java)
                        usermeals.add(meal)
                    }
                }
        }
    }
    if (usermeals.isEmpty()) { return }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(count = usermeals.size) { index ->
            val meal = usermeals[index]
            Box(
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(Color(0xFF4C566A))
                    .clickable {
                        onMealSelected(meal)
                        Log.d("UserMeal", "Selected meal: $meal")
                        Toast.makeText(context, "Meal selected!", Toast.LENGTH_SHORT).show()
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF434D66))
                        .padding(22.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = meal.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                    Text(
                        text = "${meal.calories} kcal",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .border(
                        width = 2.dp,
                        color = Color(0xFF4C566A),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        // Handle click - e.g., navigate to custom meal entry screen
                    }
                    .padding(16.dp)
            ) {
                Text(
                    text = "+   Add a Custom Meal",
                    fontSize = 20.sp,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}






@Composable
fun UserMeals() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E3440))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Meals",
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = poppinsFontFamily,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
            fontSize = 35.sp,


        )
        Spacer(modifier = Modifier.padding(5.dp))

        UserMealsList { selectedMeal ->
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@UserMealsList
            val db = FirebaseFirestore.getInstance()

            val mealData = mapOf(
                "name" to selectedMeal.name,
                "calories" to selectedMeal.calories,
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("users").document(userId)
                .collection("logged_meals")
                .add(mealData)
        }



    }
}

@Preview
@Composable
fun UserMealsPreview() {
    UserMeals()
}