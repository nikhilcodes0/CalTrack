package com.example.calorietracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calorietracker.ROUTE_PROFILE
import com.example.calorietracker.ROUTE_SIGNUP
import com.example.calorietracker.ui.theme.poppinsFontFamily
import com.google.firebase.auth.FirebaseAuth


@Composable
fun LoginScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf("") }
    var auth: FirebaseAuth = FirebaseAuth.getInstance()


    Column (
        modifier = Modifier
            .background(Color(0xFF2E3440))
            .padding(top = 160.dp)
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Hey there,",
            fontFamily = poppinsFontFamily,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
            )

        Text(
            text = "Welcome Back",
            fontFamily = poppinsFontFamily,
            color = Color.White,
            fontSize = 35.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(top = 7.dp)

        )

        Spacer(modifier = Modifier.height(50.dp))

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
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp, horizontal = 20.dp),
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
            modifier = Modifier.fillMaxWidth().padding(vertical = 0.dp, horizontal = 20.dp),
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

        Spacer(modifier = Modifier.height(80.dp))

        Text (
            text = loginError,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFBF616A),
            fontFamily = poppinsFontFamily

        )

        Button(
            onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            navController.navigate("$ROUTE_PROFILE/$userId")
                        } else {
                            loginError = task.exception?.message ?: "Login failed!"
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
            Text(text = "Login", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = poppinsFontFamily, fontSize = 17.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account?",
                fontFamily = poppinsFontFamily,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "SignUp",
                fontFamily = poppinsFontFamily,
                color = Color(0xFF81A1C1),
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable{
                        navController.navigate(ROUTE_SIGNUP)
                    }
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    val fakeNavController = rememberNavController()
    LoginScreen(fakeNavController)
}