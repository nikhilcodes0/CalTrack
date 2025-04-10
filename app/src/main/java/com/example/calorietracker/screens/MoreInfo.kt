package com.example.calorietracker.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.VolunteerActivism

import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CrisisAlert
import androidx.compose.material.icons.rounded.Height

import androidx.compose.material.icons.rounded.Scale
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calorietracker.R
import com.example.calorietracker.ROUTE_HOME
import com.example.calorietracker.ROUTE_PROFILE
import com.example.calorietracker.ui.theme.poppinsFontFamily
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreInfoScreen(navController: NavController, userId: String) {

    val db = FirebaseFirestore.getInstance()

    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Male", "Female", "Others")
    var selectedText by remember { mutableStateOf("Choose your Gender") }
    var dob by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var submitError by remember { mutableStateOf("") }
    var activityExpanded by remember { mutableStateOf(false) }
    val activityItems = listOf("Sedentary", "Lightly Active", "Moderately Active", "Very Active")
    var selectedActivity by remember { mutableStateOf("Choose your Activity Level") }
    var fitnessExpanded by remember { mutableStateOf(false) }
    val fitnessItems = listOf("Lose Weight", "Maintain Weight", "Gain Muscle")
    var selectedGoal by remember { mutableStateOf("Choose your Fitness Goal") }



    Column(
        modifier = Modifier
            .background(Color(0xFF2E3440))
            .fillMaxSize()

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center

        ) {
            Image(
                painter = painterResource(id = R.drawable.vector),
                contentDescription = "Blob",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .scale(0.8f)
//                    .padding(top = 5.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.girl),
                contentDescription = "Girl",
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(1.5f)
//                    .padding(top = 10.dp)
            )
        }

//        Spacer(modifier = Modifier.height(80.dp))


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 0.dp)
        ) {
            Text(
                text = "Let's complete your profile",
                fontFamily = poppinsFontFamily,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )

            Text(
                text = "It will help us to know more about you!",
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 8.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier
                    .padding(top = 14.dp)
                    .wrapContentWidth()
            ) {
                TextField(
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true, // Prevents manual text input
                    modifier = Modifier.menuAnchor().fillMaxWidth().padding(35.dp, 5.dp),




                    shape = RoundedCornerShape(12.dp),

                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon", tint = Color.Gray)
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Groups, contentDescription = "Gender Icon", tint = Color.Gray)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = if (selectedText == "Choose your Gender") Color.Gray else Color.Black,
                        unfocusedTextColor = if (selectedText == "Choose your Gender") Color.Gray else Color.Black,
                        focusedContainerColor = Color(0xFF3B4252), // Background when focused
                        unfocusedContainerColor = Color(0xFF3B4252), // Background when not focused
                        cursorColor = Color.White, // Cursor color
                        
                        focusedIndicatorColor = Color.Transparent, // Removes the bottom border when focused
                        unfocusedIndicatorColor = Color.Transparent // Removes the bottom border when not focused
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = poppinsFontFamily, // Use your custom Poppins font
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (selectedText == "Choose your Gender") Color.Gray else Color.White // Maintain text color
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color(0xFF3B4252),shape = RoundedCornerShape(0.dp))

                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item, color = Color.White, fontFamily = poppinsFontFamily) },
                            onClick = {
                                selectedText = item
                                expanded = false
                            },

//                            modifier = Modifier.clip(RoundedCornerShape(0.dp)).padding(0.dp, 0.dp).background(Color.Transparent)


                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = activityExpanded,
                onExpandedChange = { activityExpanded = it },
                modifier = Modifier
                    .padding(top = 14.dp)
                    .wrapContentWidth()
            ) {
                TextField(
                    value = selectedActivity,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(35.dp, 5.dp),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon", tint = Color.Gray)
                    },
                    leadingIcon = {
                        Icon(Icons.Default.VolunteerActivism, contentDescription = "Activity Icon", tint = Color.Gray)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = if (selectedActivity == "Choose your Activity Level") Color.Gray else Color.White,
                        unfocusedTextColor = if (selectedActivity == "Choose your Activity Level") Color.Gray else Color.White,
                        focusedContainerColor = Color(0xFF3B4252),
                        unfocusedContainerColor = Color(0xFF3B4252),
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = poppinsFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (selectedActivity == "Choose your Activity Level") Color.Gray else Color.White
                    )
                )

                ExposedDropdownMenu(
                    expanded = activityExpanded,
                    onDismissRequest = { activityExpanded = false },
                    modifier = Modifier.background(Color(0xFF3B4252), shape = RoundedCornerShape(0.dp))
                ) {
                    activityItems.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item, color = Color.White, fontFamily = poppinsFontFamily) },
                            onClick = {
                                selectedActivity = item
                                activityExpanded = false
                            }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = fitnessExpanded,
                onExpandedChange = { fitnessExpanded = it },
                modifier = Modifier
                    .padding(top = 14.dp)
                    .wrapContentWidth()
            ) {
                TextField(
                    value = selectedGoal,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(35.dp, 5.dp),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon", tint = Color.Gray)
                    },
                    leadingIcon = {
                        Icon(Icons.Default.AddTask, contentDescription = "Goal Icon", tint = Color.Gray)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = if (selectedGoal == "Choose your Fitness Goal") Color.Gray else Color.White,
                        unfocusedTextColor = if (selectedGoal == "Choose your Fitness Goal") Color.Gray else Color.White,
                        focusedContainerColor = Color(0xFF3B4252),
                        unfocusedContainerColor = Color(0xFF3B4252),
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = poppinsFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (selectedGoal == "Choose your Fitness Goal") Color.Gray else Color.White
                    )
                )

                ExposedDropdownMenu(
                    expanded = fitnessExpanded,
                    onDismissRequest = { fitnessExpanded = false },
                    modifier = Modifier.background(Color(0xFF3B4252), shape = RoundedCornerShape(0.dp))
                ) {
                    fitnessItems.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item, color = Color.White, fontFamily = poppinsFontFamily) },
                            onClick = {
                                selectedGoal = item
                                fitnessExpanded = false
                            }
                        )
                    }
                }
            }

            TextField(
                value = dob,
                onValueChange = {dob = it},
                label = { Text(text = "Enter your Date Of Birth", fontWeight = FontWeight.Light, color = Color.Gray, fontFamily = poppinsFontFamily)},
                leadingIcon = {
                    Icon( Icons.Rounded.CalendarMonth,
                        contentDescription = "DOB",
                        tint = Color.Gray
                    )
                },




                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 35.dp).fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF3B4252), // Background when focused
                    unfocusedContainerColor = Color(0xFF3B4252), // Background when not focused
                    cursorColor = Color.White, // Cursor color
                    focusedTextColor = Color.White, // Text color when focused
                    unfocusedTextColor = Color.White, // Text color when not focused,
                    focusedIndicatorColor = Color.Transparent, // Removes the bottom border when focused
                    unfocusedIndicatorColor = Color.Transparent // Removes the bottom border when not focused
                ),

                )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(35.dp, 0.dp)

            ) {
                TextField(
                    value = weight,
                    onValueChange = {weight = it},
                    label = { Text(text = "Enter your Weight", fontSize = 14.sp ,fontWeight = FontWeight.Light, color = Color.Gray, fontFamily = poppinsFontFamily)},
                    leadingIcon = {
                        Icon( Icons.Rounded.Scale,
                            contentDescription = "DOB",
                            tint = Color.Gray
                        )
                    },




                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.width(320.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF3B4252), // Background when focused
                        unfocusedContainerColor = Color(0xFF3B4252), // Background when not focused
                        cursorColor = Color.White, // Cursor color
                        focusedTextColor = Color.White, // Text color when focused
                        unfocusedTextColor = Color.White, // Text color when not focused,
                        focusedIndicatorColor = Color.Transparent, // Removes the bottom border when focused
                        unfocusedIndicatorColor = Color.Transparent // Removes the bottom border when not focused
                    ),

                    )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                        .background(Color(0xFF3B4252)) // Same background as TextField
                        .padding(18.dp) // Padding inside the shape
                ) {
                    Text(
                        text = "KG",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(35.dp, 20.dp)

            ) {
                TextField(
                    value = height,
                    onValueChange = { height = it },
                    label = {
                        Text(
                            text = "Enter your height",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Gray,
                            fontFamily = poppinsFontFamily
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Rounded.Height,
                            contentDescription = "DOB",
                            tint = Color.Gray
                        )
                    },


                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.width(320.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF3B4252), // Background when focused
                        unfocusedContainerColor = Color(0xFF3B4252), // Background when not focused
                        cursorColor = Color.White, // Cursor color
                        focusedTextColor = Color.White, // Text color when focused
                        unfocusedTextColor = Color.White, // Text color when not focused,
                        focusedIndicatorColor = Color.Transparent, // Removes the bottom border when focused
                        unfocusedIndicatorColor = Color.Transparent // Removes the bottom border when not focused
                    ),

                    )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                        .background(Color(0xFF3B4252)) // Same background as TextField
                        .padding(17.dp) // Padding inside the shape
                ) {
                    Text(
                        text = "CM",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(35.dp, 0.dp)

            ) {
                TextField(
                    value = calories,
                    onValueChange = { calories = it },
                    label = {
                        Text(
                            text = "Enter your daily calories goal",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Gray,
                            fontFamily = poppinsFontFamily
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Rounded.CrisisAlert,
                            contentDescription = "DOB",
                            tint = Color.Gray
                        )
                    },


                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.width(320.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF3B4252), // Background when focused
                        unfocusedContainerColor = Color(0xFF3B4252), // Background when not focused
                        cursorColor = Color.White, // Cursor color
                        focusedTextColor = Color.White, // Text color when focused
                        unfocusedTextColor = Color.White, // Text color when not focused,
                        focusedIndicatorColor = Color.Transparent, // Removes the bottom border when focused
                        unfocusedIndicatorColor = Color.Transparent // Removes the bottom border when not focused
                    ),

                    )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
                        .background(Color(0xFF3B4252)) // Same background as TextField
                        .padding(17.dp) // Padding inside the shape
                ) {
                    Text(
                        text = "kCal",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            }

            Text (
                text = submitError,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFBF616A),
                fontFamily = poppinsFontFamily,
                modifier = Modifier
                    .padding(top = 15.dp)

            )
            fun calculateBMR(gender: String, weight: Double, height: Double, age: Int): Double {
                return if (gender.lowercase() == "male") {
                    10 * weight + 6.25 * height - 5 * age + 5
                } else {
                    10 * weight + 6.25 * height - 5 * age - 161
                }
            }

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


            Button(
                onClick = {
                    if (selectedText.isNotEmpty() && weight.isNotEmpty() && dob.isNotEmpty() && height.isNotEmpty() && calories.isNotEmpty() && selectedGoal.isNotEmpty() && selectedActivity.isNotEmpty()) {
                        val bmr = calculateBMR(selectedText, weight.toDouble(), height.toDouble(), calculateAge(dob).toInt())
                        val userMap = mapOf(
                            "gender" to selectedText,
                            "weight" to weight,
                            "dob" to dob,
                            "height" to height,
                            "calories" to calories,
                            "activity" to selectedActivity,
                            "goal" to selectedGoal,
                            "bmr" to bmr
                        )

                        db.collection("users").document(userId)
                            .update(userMap)
                            .addOnSuccessListener {

                                navController.navigate("$ROUTE_HOME/$userId") // Redirect to profile after saving
                            }
                            .addOnFailureListener { e ->
                                submitError = "Error: ${e.message}"
                            }
                    } else {
                        submitError = "Please fill all fields!"
                    }
                },

                contentPadding = PaddingValues(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4C566A)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
//                    .padding(bottom = 40.dp)
                    .padding(top = 40.dp)

                    .padding(horizontal = 35.dp)

            ) {
                Text(text = "Get Started", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = poppinsFontFamily, fontSize = 17.sp)
            }

        }

    }
}


@Preview
@Composable
fun MoreInfoScreenPreview() {
    MoreInfoScreen(navController = rememberNavController(), userId = "123")
}


