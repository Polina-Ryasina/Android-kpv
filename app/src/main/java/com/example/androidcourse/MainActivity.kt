package com.example.androidcourse
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivitiesController.addActivity(this)
        enableEdgeToEdge()
        setContent {
            FirstScreen()
        }
    }

    @Composable
    fun FirstScreen() {
        var text by remember { mutableStateOf("")}
        val context = this

        Column(
            modifier = Modifier.fillMaxSize().background(Color(0xFFc7d3ef)),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = text,
                onValueChange = { input -> text = input},
                label = { Text("Ввод текста", color = Color(0xFF05296E))},
                modifier = Modifier.fillMaxWidth().height(60.dp).padding(horizontal = 18.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF819ad5),
                    unfocusedContainerColor = Color(0xFF8ba6e5),
                    focusedIndicatorColor = Color(0xFF34549d),
                    unfocusedIndicatorColor = Color(0xFF204db6),
                    cursorColor = Color(0xFF214391)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    val intent = Intent(context, SecondActivity::class.java)
                    if (text != "") {
                        intent.putExtra("text", text)
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal= 18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1144AA)
                )
            ) {Text("На 2 экран", fontSize = 20.sp, color = Color(0xFFb5cbf8)) }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    val intent = Intent(context, ThirdActivity::class.java)
                    if (text != "") {
                        intent.putExtra("text", text)
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal=18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1144AA)
                )
            ) {Text("На 3 экран", fontSize = 20.sp, color = Color(0xFFb5cbf8)) }
        }
    }
}
