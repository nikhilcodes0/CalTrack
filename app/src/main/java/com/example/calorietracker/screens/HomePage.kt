package com.example.calorietracker.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calorietracker.ROUTE_MEALS
import com.example.calorietracker.ui.theme.poppinsFontFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin


fun calculateTDEE(bmr: Float, activityLevel: String): Double {
    val multiplier = when (activityLevel) {
        "Sedentary" -> 1.2
        "Lightly active" -> 1.375
        "Moderately active" -> 1.55
        "Very active" -> 1.725
        else -> 1.2
    }
    return bmr * multiplier
}


@SuppressLint("DefaultLocale")

@Composable
fun BMIPieChart(bmi: Float, modifier: Modifier = Modifier) {
    val total = 100f // Max BMI scale
    val bmiPercentage = (bmi / total).coerceIn(0f, 1f)
    val bmiAngle = bmiPercentage * 360f
    bmi.let {
        Log.d("BMI_CHECK", it.toString())
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(100.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val radius = size.minDimension / 2

            val center = Offset(x = canvasWidth / 2, y = canvasHeight / 2)

            // Draw background circle
            drawArc(
                color = Color.White,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = true
            )

            // Offset angle (center of the BMI arc)
            val offsetAngle = -10f + bmiAngle / 2
            val radians = Math.toRadians(offsetAngle.toDouble())

            // Offset direction
            val offsetDistance = 10.dp.toPx() // How far the BMI arc pops out
            val dx = (cos(radians) * offsetDistance).toFloat()
            val dy = (sin(radians) * offsetDistance).toFloat()

            // Draw BMI arc slightly outside
            drawArc(
                color = Color(0xFF2E3440),
                startAngle = -100f,
                sweepAngle = bmiAngle,
                useCenter = true,
                topLeft = Offset(-dx, -dy),
                size = Size(size.width + dx * 2, size.height + dy * 2)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Text("BMI", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(
                String.format("%.1f", bmi),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 55.dp, start = 45.dp),
                color = Color.White
            )
        }
    }
}


@Composable
fun HomePage(navController: NavController, userId: String) {
    var auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val db = FirebaseFirestore.getInstance()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    if (currentUserId == null) {
        return@HomePage
    }
    var goal by remember { mutableFloatStateOf(2000f) }

    var progress by remember { mutableFloatStateOf(0f) }

    var bmr by remember { mutableFloatStateOf(0f) }
    var activityLevel by remember { mutableStateOf("") }


// Get today's start timestamp
    LaunchedEffect(currentUserId, goal) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.timeInMillis

        db.collection("users").document(currentUserId)
            .collection("logged_meals")
            .whereGreaterThanOrEqualTo("timestamp", startOfDay)
            .get()
            .addOnSuccessListener { documents ->
                var totalCalories = 0
                for (doc in documents) {
                    totalCalories += (doc.getLong("calories") ?: 0L).toInt()
                }

                progress = (totalCalories.toFloat() / goal).coerceIn(0f, 1f)

            }
    }


    var userData by remember { mutableStateOf<Map<String, Any>?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var bmi by remember { mutableStateOf<Float?>(null) }
    var calories by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(currentUserId) {
        try {
            val document = firestore.collection("users").document(userId).get().await()
            if (document.exists()) {
                userData = document.data
                val weight = (userData?.get("weight") as? String)?.toFloatOrNull() ?: 0f
                val heightCm = (userData?.get("height") as? String)?.toFloatOrNull() ?: 0f

                Log.d("BMI_INPUTS", "Weight: $weight, Height: $heightCm")
                val heightM = heightCm / 100f

                val activityLevelString = userData?.get("activity") as? String
                activityLevel = activityLevelString ?: "Sedentary"
                Log.d("BMI_ACTIVITY", "Activity Level: $activityLevel")

                val bmrValue = userData?.get("bmr") as? Double
                bmr = bmrValue?.toFloat() ?: 0f
                Log.d("BMI_BMR", "BMR: $bmr")

                val caloriesString = userData?.get("calories") as? String
                val caloriesGoal = caloriesString?.toFloatOrNull() ?: 2000f
                goal = caloriesGoal

                val calculatedBMI = if (heightM > 0) weight / (heightM * heightM) else 0f
                bmi = calculatedBMI
            } else {
                error = "User data not found!"
            }
        } catch (e: Exception) {
            error = e.message ?: "Failed to load user data!"
        }
    }
    LaunchedEffect(bmi) {
        Log.d("BMI_LOG", "BMI value is: $bmi")

    }




    Column(
        modifier = Modifier
            .background(Color(0xFF2E3440))
            .fillMaxSize()
            .padding(28.dp, 38.dp)

    ) {
        Text(
            text = "Welcome Back,",
            fontFamily = poppinsFontFamily,
            modifier = Modifier.padding(bottom = 10.dp),
            color = Color.Gray,
            fontWeight = FontWeight.Black,
            fontSize = 25.sp
        )

        Text(
            text = "H4WK",
            color = Color.White,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 35.sp,
            modifier = Modifier.padding(bottom = 22.dp)
        )

        Box(
            modifier = Modifier
                .shadow(
                    15.dp,
                    RoundedCornerShape(16.dp),
                    ambientColor = Color(0xFF6F7787),
                    spotColor = Color(0xFF6F7787)
                )
                .fillMaxWidth()
                .background(Color(0xFF4C566A))
                .padding(20.dp)

        ) {
            Row(

            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 30.dp)
                ) {
                    Text(
                        text = "BMI (Body Mass Index)",
                        color = Color.White,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp
                    )

                    Text(
                        text = "You have a normal weight",
                        color = Color.LightGray,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }


               if(bmi != null){
                BMIPieChart(bmi = bmi!!, modifier = Modifier.padding(start = 60.dp))
               }


            }
        }

        Spacer(modifier = Modifier.height(23.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF4C566A))
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Log Calories",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    fontSize = 17.sp,

                    )

                Button(
                    onClick = {
                        navController.navigate("$ROUTE_MEALS/$userId")
                    },
//                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF92A3FD)
                    ),
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(
                        text = "Log",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        fontSize = 14.sp,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(23.dp))

        Text(
            text = "Activity Status",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Black,
            color = Color.White,
            fontSize = 17.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF4C566A))
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "Calories",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "${(progress * goal).toInt()} / ${goal.toInt()} kCal",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF92A3FD),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp)
                        .clip(RoundedCornerShape(50)), // Rounded edges
                    color = Color(0xFF88C0D0),        // Filled color
                    trackColor = Color(0xFF3B4252),   // Unfilled color
                    strokeCap = StrokeCap.Round,
                )
            }
        }

        Spacer(modifier = Modifier.height(23.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF4C566A))
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "TDEE",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "${(progress * goal).toInt()} / ${calculateTDEE(bmr, activityLevel)} kCal",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF92A3FD),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp)
                        .clip(RoundedCornerShape(50)), // Rounded edges
                    color = Color(0xFF88C0D0),        // Filled color
                    trackColor = Color(0xFF3B4252),   // Unfilled color
                    strokeCap = StrokeCap.Round,
                )
            }
        }

    }
}

@Preview
@Composable
fun HomePagePreview() {
    HomePage(navController = NavController(LocalContext.current), userId = "123")
}

