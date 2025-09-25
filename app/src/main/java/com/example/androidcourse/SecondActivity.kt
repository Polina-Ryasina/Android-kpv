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

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecondScreen()
        }
    }

    @Composable
    fun SecondScreen() {
        val text = intent.getStringExtra("text") ?: "Экран 2"
        val context = this

        Column(
            modifier = Modifier.fillMaxSize().background(Color(0xFFf2caec)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 18.dp).padding(bottom = 25.dp),
                color = Color(0xFF730065)
            )

            Button(
                onClick = {
                    val intent = Intent(context, ThirdActivity::class.java)
                    if (text != "Экран 2") {
                        intent.putExtra("text", text)
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal = 18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF852178)
                )
            ) {
                Text("На 3 экран", fontSize = 20.sp, color = Color(0xFFe5b8db))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal = 18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF852178)
                )
            ) {
                Text("На 1 экран", fontSize = 20.sp, color = Color(0xFFe5b8db))
            }
        }
    }
}
