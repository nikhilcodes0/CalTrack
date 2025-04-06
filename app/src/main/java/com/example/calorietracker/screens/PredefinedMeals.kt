package com.example.calorietracker.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calorietracker.R
import com.example.calorietracker.ui.theme.poppinsFontFamily
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.firestore.FirebaseFirestore

data class PredefinedMeal(
    val name: String = "",
    val calories: Int = 0,
)



@Composable
fun PredefinedMealsList(
    onMealSelected: (PredefinedMeal) -> Unit
) {
    val meals = remember { mutableStateListOf<PredefinedMeal>() }
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("meals").get()
            .addOnSuccessListener { result ->
                meals.clear()
                for (document in result) {
                    val meal = document.toObject(PredefinedMeal::class.java)
                    meals.add(meal)
                }
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(meals) { meal ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMealSelected(meal)
                        Log.d("SelectedMeal", "Meal selected: $meal")
                        Toast.makeText(context, "Meal logged successfully!", Toast.LENGTH_SHORT).show()
                    }
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = meal.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "${meal.calories} kcal", style = MaterialTheme.typography.bodyMedium)
            }
            Divider()
        }
    }
}


@Composable
fun PredefinedMeals() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E3440))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Predefined Meals",
            color = Color.White,
            modifier = Modifier.padding(top = 30.dp),
            fontFamily = poppinsFontFamily,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
            fontSize = 25.sp
        )

//        Column(
//            modifier = Modifier
//                .padding(top = 30.dp)
//                .fillMaxWidth()
//
//        ) {
//            Box (
//                modifier = Modifier
//                    .clip(RoundedCornerShape(10.dp))
//                    .fillMaxWidth()
//                    .background(Color(0xFF4C566A)),
//            ) {
//                Row(
//                    modifier = Modifier
//                        .padding(13.dp)
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//
//                    Text(
//                        text = "Predefined Meals",
//                        color = Color.White,
//                        fontFamily = poppinsFontFamily,
//                        fontWeight = FontWeight.Black,
//                        fontSize = 15.sp
//                    )
//
//                    Text(
//                        text = "200kCal",
//                        color = Color.White,
//                        fontFamily = poppinsFontFamily,
//                        fontWeight = FontWeight.Black,
//                        fontSize = 15.sp
//                    )
//                }
//            }
//        }

        PredefinedMealsList { selectedMeal ->
            // Save the selected meal under current user
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@PredefinedMealsList
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
fun PredefinedMealsPreview() {
    PredefinedMeals()

}