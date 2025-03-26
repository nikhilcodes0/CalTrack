package com.example.calorietracker.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
                    .height(400.dp)
                    .padding(top = 50.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.man),
                contentDescription = "Man",
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(3.3f)
                    .padding(end = 30.dp)
                    .padding(top = 10.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.lady_2_),
                contentDescription = "Girl",
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(3.3f)
                    .padding(start = 25.dp)
                    .padding(top = 10.dp)
            )
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally
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
                        .width(60.dp)
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
                        .width(60.dp)
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
                        .width(60.dp)
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
                fontWeight = FontWeight.Black
            )
        }
    }





}





