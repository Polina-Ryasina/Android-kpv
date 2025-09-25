package com.example.androidcourse

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThirdScreen()
        }
    }

    @Composable
    fun ThirdScreen() {
        val text = intent.getStringExtra("text") ?: "Экран 3"
        val context = this

        Column(
            modifier = Modifier.fillMaxSize().background(Color(0xFFc0ece8)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 25.dp).padding(horizontal = 18.dp),
                color = Color(0xFF00675C)
            )

            Button(
                onClick = {
                    MainActivitiesController.closeAll()
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal = 18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E776D)
                )
            ) {
                Text("На 1 экран", fontSize = 20.sp, color = Color(0xFF46ddcb))
            }
        }
    }
}
