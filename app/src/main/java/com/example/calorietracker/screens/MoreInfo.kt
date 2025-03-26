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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Groups

import androidx.compose.material.icons.rounded.CalendarMonth
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
import com.example.calorietracker.ROUTE_PROFILE
import com.example.calorietracker.ui.theme.poppinsFontFamily
import com.google.firebase.firestore.FirebaseFirestore


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
    var submitError by remember { mutableStateOf("") }



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
                    .height(290.dp)
                    .scale(1.5f)
                    .padding(top = 65.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.girl),
                contentDescription = "Girl",
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(2.8f)
                    .padding(top = 40.dp)
            )
        }

        Spacer(modifier = Modifier.height(110.dp))


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 20.dp)
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

            Text (
                text = submitError,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFBF616A),
                fontFamily = poppinsFontFamily,
                modifier = Modifier
                    .padding(top = 15.dp)

            )

            Button(
                onClick = {
                    if (selectedText.isNotEmpty() && weight.isNotEmpty() && dob.isNotEmpty() && height.isNotEmpty()) {
                        val userMap = mapOf(
                            "gender" to selectedText,
                            "weight" to weight,
                            "dob" to dob,
                            "height" to height
                        )

                        db.collection("users").document(userId)
                            .update(userMap)
                            .addOnSuccessListener {
                                navController.navigate("$ROUTE_PROFILE/$userId") // Redirect to profile after saving
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


