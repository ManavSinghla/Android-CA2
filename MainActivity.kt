package com.example.ca2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ca2.ui.theme.CA2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CA2Theme {
                nav()
            }
        }
    }
}

@Composable
fun Login( navController : NavController){
    var email by remember { mutableStateOf( value="")}
    var password by remember { mutableStateOf( value="")}
    var context = LocalContext.current
    var rememberMe by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login Screen")
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") }
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") }
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it }
            )
            Text("Remember Me")
        }

        Button(
            onClick = {
                if (!email.contains("@")){
                    Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show()
                }
                if (password.length<6){
                    Toast.makeText(context, "Invalid Password", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    navController.navigate("Welcome/$rememberMe")
                }
            }
        ) {
            Text("Login")
        }
    }
}

@Composable
fun Welcome(rememberMe: Boolean){
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(rememberMe){
            Text(text = "Welcome Back")
        }
        else{
            Text(text = "Welcome")
        }
    }
}

@Composable
fun nav(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") {
            Login(navController)
        }
        composable("Welcome/{rememberMe}") {backStackEntry ->
            val rememberMe = backStackEntry.arguments?.getString("rememberMe").toBoolean()
            Welcome(rememberMe)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    nav()
}