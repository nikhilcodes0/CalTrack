package com.example.calorietracker.screens

import android.R.attr.bottom
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calorietracker.R
import com.example.calorietracker.ROUTE_LOGIN
import com.example.calorietracker.ui.theme.poppinsFontFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.input.KeyboardType
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.Period

fun calculateAge(dob: String): Int {
    return try {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val birthDate = LocalDate.parse(dob, formatter)
        val currentDate = LocalDate.now()
        Period.between(birthDate, currentDate).years
    } catch (e: Exception) {
        -1 // Return -1 in case of invalid DOB format
    }
}


@Composable
fun ProfileScreen(navController: NavController, userId: String) {
    var auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()


    var userData by remember { mutableStateOf<Map<String, Any>?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }




    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        userData = document.data

                    } else {
                        error = "User data not found!"
                    }
                }
                .addOnFailureListener { e ->
                    error = e.message ?: "Failed to load user data!"
                }
        } else {
            error = "User not logged in!"
        }
    }


    val dob = userData?.get("dob") as? String ?: "N/A"
    val age = calculateAge(dob)
    val username = userData?.get("username") as? String ?: "N/A"
    val email = userData?.get("email") as? String ?: "N/A"
    val weight = userData?.get("weight") as? String ?: "N/A"
    val height = userData?.get("height") as? String ?: "N/A"
    val calories = userData?.get("calories") as? String ?: "N/A"
    val gender = userData?.get("gender") as? String ?: "N/A"
    val activity = userData?.get("activity") as? String ?: "N/A"
    val goal = userData?.get("goal") as? String ?: "N/A"


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E3440)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Profile",
            fontFamily = poppinsFontFamily,
            color = Color.White,
            fontWeight = FontWeight.Black,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(top = 20.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.blob2),
                contentDescription = "Blob",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
                    .padding(top = 40.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.man),
                contentDescription = "Man",
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(2.8f)
                    .padding(end = 30.dp)
                    .padding(top = 5.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.lady_2_),
                contentDescription = "Girl",
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(2.8f)
                    .padding(start = 25.dp)
                    .padding(top = 5.dp)
            )
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 10.dp)
        ){
            Text(
                text = username,
                fontSize = 40.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Black,

                color = Color.White,
                modifier = Modifier
                    .padding(top = 20.dp)
            )

            Text(
                text = email,
                fontSize = 15.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Black,

                color = Color.Gray,
                modifier = Modifier
//                    .padding(top = 20.dp)
            )

            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 30.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                        .background(Color(0xFF3B4252)) // Same background as TextField
                        .padding(18.dp)
                        .width(80.dp)
                        .wrapContentSize(Alignment.Center)



                ) {
                    Text(
                        text = height + "cm",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
//                        modifier = Modifier
//                            .
                    )

                    Text(
                        text = "Height",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(top = 30.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                        .background(Color(0xFF3B4252)) // Same background as TextField
                        .padding(18.dp)
                        .width(80.dp)
                        .wrapContentSize(Alignment.Center)
                ) {
                    Text(
                        text = weight + "kg",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(50.dp)
                    )

                    Text(
                        text = "Weight",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .width(50.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                        .background(Color(0xFF3B4252)) // Same background as TextField
                        .padding(18.dp) // Padding inside the shape
                        .width(80.dp)
                        .wrapContentSize(Alignment.Center)
                ) {

                    Text(
                        text = "$age" + "yo",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(50.dp)
                    )

                    Text(
                        text = "Age",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .width(50.dp),
//                            .padding(start = 5.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                        .background(Color(0xFF3B4252)) // Same background as TextField
                        .padding(18.dp)
                        .width(160.dp)
                        .wrapContentSize(Alignment.Center)
                        ,




                ) {
                    Text(
                        text = calories + " kCal",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Text(
                        text = "Calories Goal",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,

                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth()
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                        .background(Color(0xFF3B4252)) // Same background as TextField
                        .padding(18.dp)
                        .width(160.dp)
                        .wrapContentSize(Alignment.Center)
                ) {
                    Text(
                        text = gender,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(50.dp)
                    )

                    Text(
                        text = "Gender",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .width(60.dp)
                    )
                }

            }

            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                        .background(Color(0xFF3B4252)) // Same background as TextField
                        .padding(18.dp)
                        .width(160.dp)
                        .wrapContentSize(Alignment.Center)
                    ,




                    ) {
                    Text(
                        text = goal,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Text(
                        text = " Goal",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,

                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth()
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                        .background(Color(0xFF3B4252)) // Same background as TextField
                        .padding(18.dp)
                        .width(160.dp)
                        .wrapContentSize(Alignment.Center)
                ) {
                    Text(
                        text = activity,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Activity",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth()
                    )
                }

            }
        }

        Button(
            onClick = {
                showEditDialog = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, Color(0xFF4C566A)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 0.dp)
        ) {
            Text(
                text = "Edit Profile",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Black,
                color = Color(0xFF4C566A) // Match the border color
            )
        }

        if (showEditDialog) {
            EditProfileDialog(
                userData = userData,
                onDismiss = { showEditDialog = false },
                userId = auth.currentUser?.uid ?: ""
            )
        }


        Button(
            onClick = {
            auth.signOut()
                navController.navigate(ROUTE_LOGIN)

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4C566A)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 0.dp)
        ){
            Text(
                text = "Log Out",
                fontSize = 16.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
        }
    }





}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    userData: Map<String, Any>?, // e.g., from Firestore
    onDismiss: () -> Unit,
    userId: String
) {
    var username by remember { mutableStateOf(userData?.get("username") as? String ?: "") }
    var weight by remember { mutableStateOf(userData?.get("weight") as? String ?: "") }
    var height by remember { mutableStateOf(userData?.get("height")as? String  ?: "") }
    var calories by remember { mutableStateOf(userData?.get("calories")as? String  ?: "") }
    var activity by remember { mutableStateOf(userData?.get("activity")as? String  ?: "") }
    var goal by remember { mutableStateOf(userData?.get("goal")as? String  ?: "") }

    val goalOptions = listOf("Lose Weight", "Maintain Weight", "Gain Weight")
    val activityOptions = listOf("Sedentary", "Lightly Active", "Moderately Active", "Very Active")

    var expandedGoal by remember { mutableStateOf(false) }
    var expandedActivity by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Profile", color = Color.White) },
        text = {
            Column {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = "Username", color = Color.White) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text(text = "Weight (kg)", color = Color.White) },

                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    label = { Text(text = "Height (cm)", color = Color.White) },

                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = calories,
                    onValueChange = { calories = it },
                    label = { Text(text = "Calorie Goal (kCal)", color = Color.White) },

                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = expandedGoal,
                    onExpandedChange = { expandedGoal = !expandedGoal }
                ) {
                    OutlinedTextField(
                        value = goal,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Goal", color = Color.White) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGoal) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedGoal,
                        onDismissRequest = { expandedGoal = false }
                    ) {
                        goalOptions.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    goal = it
                                    expandedGoal = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Activity Dropdown
                ExposedDropdownMenuBox(
                    expanded = expandedActivity,
                    onExpandedChange = { expandedActivity = !expandedActivity }
                ) {
                    OutlinedTextField(
                        value = activity,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Activity Level", color = Color.White) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedActivity) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedActivity,
                        onDismissRequest = { expandedActivity = false }
                    ) {
                        activityOptions.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    activity = it
                                    expandedActivity = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val db = Firebase.firestore
                val userRef = db.collection("users").document(userId)

                val updatedData = mapOf(
                    "username" to username,
                    "weight" to weight,
                    "height" to height,
                    "calories" to calories,
                    "goal" to goal,
                    "activity" to activity
                )

                userRef.update(updatedData)
                    .addOnSuccessListener {
                        Log.d("Firestore", "User updated")
                        onDismiss()
                    }
                    .addOnFailureListener {
                        Log.e("Firestore", "Error updating", it)
                    }
            }

            ) {
                Text("Save", color = Color.White)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel", color = Color.White)
            }
        },
        containerColor = Color(0xFF3B4252),
        titleContentColor = Color.White,
        textContentColor = Color.White
    )
}




@Preview
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController, "userId")
}





