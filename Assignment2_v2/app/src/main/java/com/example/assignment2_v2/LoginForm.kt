package com.example.assignment2_v2

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


@Serializable
data class loginResponse(
    val email : String?,
    val password : String?
)

@Composable
fun login(snackbarHostState: androidx.compose.material3.SnackbarHostState) {
    var accountText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Image(
//            painter = painterResource(R.drawable.loginbkg),
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize()
//        )
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                title()
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = accountText,
                    onValueChange = { accountText = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Account") },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = passwordText,
                    onValueChange = { passwordText = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Password") },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            var data = loginResponse(accountText, passwordText)
                            val stringBody: String = KtorClient.postFeedback(data)
                            snackbarHostState.showSnackbar(stringBody)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = "Log in")
                }
            }
        }
    }
}

@Composable
fun title() {
    Text(
        text = "Login",
        fontSize = 30.sp,
        color = Color(0xFF8692F7),
    )
}

@Composable
fun Login(snackbarHostState: androidx.compose.material3.SnackbarHostState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        login(snackbarHostState)
    }
}

//@Composable
//fun LoginScreen() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center,
//    ) {
//        Card(
//            modifier = Modifier
//                .padding(30.dp)
//                .clip(RoundedCornerShape(30.dp))
//                .elevation(20.dp),
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(24.dp)
//                    .background(Color.White, RoundedCornerShape(30.dp))
//            ) {
//                Text(
//                    text = "Login",
//                    modifier = Modifier.fillMaxWidth(),
//                    textAlign = TextAlign.Center,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 36.sp,
//                    color = Color(0xFF8692F7)
//                )
//                TextField(
//                    value = "",
//                    onValueChange = {},
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 40.dp),
//                    label = { Text(text = "Username") },
//                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
//                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                        textColor = Color.Black,
//                        focusedBorderColor = Color(0xFF8692F7),
//                        focusedLabelColor = Color(0xFF8692F7),
//                        cursorColor = Color(0xFF8692F7),
//                    )
//                )
//                TextField(
//                    value = "",
//                    onValueChange = {},
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 20.dp),
//                    label = { Text(text = "Password") },
//                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
//                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                        textColor = Color.Black,
//                        focusedBorderColor = Color(0xFF8692F7),
//                        focusedLabelColor = Color(0xFF8692F7),
//                        cursorColor = Color(0xFF8692F7),
//                    ),
//                    visualTransformation = PasswordVisualTransformation()
//                )
//                Button(
//                    onClick = {
//
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 30.dp)
//                        .height(60.dp),
//                    shape = RoundedCornerShape(20.dp),
//                    //colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF8692F7))
//                ) {
//                    Text(text = "Login", fontSize = 18.sp)
//                }
//            }
//        }
//    }
//}