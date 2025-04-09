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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
            Box(
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(Color(0xFF4C566A))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onMealSelected(meal)
                            Log.d("SelectedMeal", "Meal selected: $meal")

                        }
                        .background(Color(0xFF434D66))
                        .padding(22.dp)
                    ,

                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = meal.name, style = MaterialTheme.typography.bodyLarge, fontSize = 22.sp, color = Color.White)
                    Text(text = "${meal.calories} kcal", style = MaterialTheme.typography.bodyMedium, fontSize = 20.sp, color = Color.White)
                }
            }


        }
    }
}

@Composable
fun LogMealDialog(
    meal: PredefinedMeal,
    onDismiss: () -> Unit,
    onSubmit: (Int) -> Unit
) {
    val gramsInput = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Log Meal", fontSize = 20.sp) },
        text = {
            Column {
                Text(text = "How much ${meal.name} did you eat (in grams)?")
                OutlinedTextField(
                    value = gramsInput.value,
                    onValueChange = { gramsInput.value = it },
                    label = { Text("Grams") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Text(
                "Submit",
                modifier = Modifier
                    .clickable {
                        val grams = gramsInput.value.toIntOrNull()
                        if (grams != null && grams > 0) {
                            onSubmit(grams)

                            onDismiss()
                        }
                    }
                    .padding(8.dp),
                color = MaterialTheme.colorScheme.primary
            )
        },
        dismissButton = {
            Text(
                "Cancel",
                modifier = Modifier
                    .clickable { onDismiss() }
                    .padding(8.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        },
        containerColor = Color(0xFF3B4252),
        titleContentColor = Color.White,
        textContentColor = Color.White
    )
}



@Composable
fun PredefinedMeals() {
    val selectedMeal = remember { mutableStateOf<PredefinedMeal?>(null) }
    val showDialog = remember { mutableStateOf(false) }


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
            modifier = Modifier.padding(top = 30.dp, bottom = 30.dp),
            fontFamily = poppinsFontFamily,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
            fontSize = 25.sp,

        )



        PredefinedMealsList { meal ->
            selectedMeal.value = meal
            showDialog.value = true
        }

        if (showDialog.value && selectedMeal.value != null) {
            LogMealDialog(
                meal = selectedMeal.value!!,
                onDismiss = { showDialog.value = false },
                onSubmit = { grams ->
                    val caloriesConsumed = (selectedMeal.value!!.calories * grams) / 100f
                    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@LogMealDialog
                    val db = FirebaseFirestore.getInstance()

                    val mealData = mapOf(
                        "name" to selectedMeal.value!!.name,
                        "calories" to caloriesConsumed,
                        "timestamp" to System.currentTimeMillis()
                    )

                    db.collection("users").document(userId)
                        .collection("logged_meals")
                        .add(mealData)

                    showDialog.value = false
                }
            )
        }
    }
}

@Preview
@Composable
fun PredefinedMealsPreview() {
    PredefinedMeals()

}