package com.example.calorietracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.AccessibilityNew
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calorietracker.ROUTE_INFO
import com.example.calorietracker.ROUTE_LOGIN
import com.example.calorietracker.ROUTE_PROFILE
import com.example.calorietracker.ui.theme.poppinsFontFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SignUpScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false)}
    var passwordError by remember { mutableStateOf(" ") }
    var username by remember { mutableStateOf("") }
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()


    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(paddingValues)
            .background(Color(0xFF2E3440))
            .padding(0.dp, 40.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Hey there,",
            fontFamily = poppinsFontFamily,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier
                .padding(top = 45.dp)

        )

        Text(
            text = "Create an Account",
            color = Color.White,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 32.sp,
            modifier = Modifier
                .padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(105.dp))


        TextField(
            value = username,
            onValueChange = {username = it},
            label = { Text(text = "Enter your username", fontWeight = FontWeight.Light, color = Color.Gray, fontFamily = poppinsFontFamily)},
            leadingIcon = {
                Icon( Icons.Rounded.AccessibilityNew,
                    contentDescription = "Account Icon",
                    tint = Color.White
                )
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 20.dp),
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


        TextField(
            value = email,
            onValueChange = {email = it},
            label = { Text(text = "Enter your email", fontWeight = FontWeight.Light, color = Color.Gray, fontFamily = poppinsFontFamily)},
            leadingIcon = {
                Icon( Icons.Rounded.AccountCircle,
                    contentDescription = "Account Icon",
                    tint = Color.White
                )
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 20.dp),
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

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = {password = it},
            label = { Text(text = "Enter your password", fontWeight = FontWeight.Light, color = Color.Gray, fontFamily = poppinsFontFamily)},
            leadingIcon = {
                Icon( Icons.Rounded.Lock,
                    contentDescription = "Password Icon",
                    tint = Color.White
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = "Toggle Password", tint = Color.White)
                }
            },


            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 20.dp),
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

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = repassword,
            onValueChange = {repassword = it},
            label = { Text(text = "Retype your password", fontWeight = FontWeight.Light, color = Color.Gray, fontFamily = poppinsFontFamily)},
            leadingIcon = {
                Icon( Icons.Rounded.Lock,
                    contentDescription = "Password Icon",
                    tint = Color.White
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = "Toggle Password", tint = Color.White)
                }
            },


            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 20.dp),
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

        Spacer(modifier = Modifier.height(68.dp))



        if (passwordError.isNotEmpty()) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {


                Spacer(modifier = Modifier.width(8.dp))

                Text (
                    text = passwordError,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFBF616A),
                    fontFamily = poppinsFontFamily

                )
            }
        }


        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                when {
                    username.isBlank() -> passwordError = "Enter your username"
                    password != repassword -> passwordError = "Retype your password!"
                    password.length < 8 -> passwordError = "Your Password must be a minimum of 8 characters!"
                    else -> {
                        passwordError = "" // Clear any previous errors

                        val firestore = FirebaseFirestore.getInstance()
                        val usersRef = firestore.collection("users")

                        // Check if username already exists
                        usersRef.whereEqualTo("username", username).get()
                            .addOnSuccessListener { documents ->
                                if (!documents.isEmpty) {
                                    passwordError = "Username already exists!"
                                } else {
                                    // Proceed with signup
                                    auth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                val userId = task.result?.user?.uid ?: ""

                                                // Store username in Firestore
                                                val userMap = hashMapOf(
                                                    "userId" to userId,
                                                    "username" to username,
                                                    "email" to email
                                                )

                                                usersRef.document(userId).set(userMap)
                                                    .addOnSuccessListener {
                                                        navController.navigate("$ROUTE_INFO/$userId") // Navigate after successful Firestore save
                                                    }
                                                    .addOnFailureListener { e ->
                                                        passwordError = e.message ?: "Failed to store username!"

                                                        auth.currentUser?.delete()
                                                    }
                                            } else {
                                                passwordError = task.exception?.message ?: "Signup failed!"
                                            }
                                        }
                                }
                            }
                            .addOnFailureListener { e ->
                                passwordError = e.message ?: "Error checking username!"
                            }
                    }
                }
            },


                    contentPadding = PaddingValues(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4C566A)
            ),
            modifier = Modifier
                .fillMaxWidth()

                .padding(bottom = 60.dp)

                .padding(horizontal = 20.dp)

        ) {
            Text(text = "Get Started", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = poppinsFontFamily, fontSize = 17.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account?",
                fontFamily = poppinsFontFamily,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Login",
                fontFamily = poppinsFontFamily,
                color = Color(0xFF81A1C1),
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable{
                        navController.navigate(ROUTE_LOGIN)
                    }
            )
        }


    }

}


@Preview
@Composable
fun SignUpPreview () {
    val fakeNavController = rememberNavController()
    SignUpScreen(fakeNavController)
}