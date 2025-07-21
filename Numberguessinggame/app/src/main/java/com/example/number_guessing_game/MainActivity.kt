package com.example.number_guessing_game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.number_guessing_game.ui.theme.NumberguessinggameTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NumberguessinggameTheme {
                AppUI()
            }
        }
    }

    @Composable
    fun AppUI() {
        var input by remember { mutableStateOf("") }
        var question by remember { mutableStateOf(Random.nextInt(1, 101)) }
        var output by remember { mutableStateOf("") }
        var clickCount by remember { mutableStateOf(0) }
        var gameEnded by remember { mutableStateOf(false) }

        fun restartGame() {
            question = Random.nextInt(1, 101)
            input = ""
            output = ""
            clickCount = 0
            gameEnded = false
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        ),
                        shape = RectangleShape
                    ) {
                        Text(
                            text = "Number Guessing Game",
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 43.dp, start = 10.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        shape = RectangleShape
                    ) {
                        Text(
                            text = "The Number is between 1 - 100",
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 40.dp, start = 10.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    TextField(
                        value = input,
                        onValueChange = { input = it },
                        shape = RoundedCornerShape(5.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.secondary,
                                RoundedCornerShape(5.dp)
                            ),
                        textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 26.sp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        enabled = !gameEnded
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            if (input.isBlank()) {
                                output = "Enter your guess🤨!"
                                return@Button
                            }
                            val guess = input.toIntOrNull()
                            if (guess == null) {
                                output = "Please enter a valid number."
                                return@Button
                            }
                            clickCount++
                            if (guess > question) {
                                output = "The number is smaller!"
                            } else if (guess < question) {
                                output = "The number is larger!"
                            } else {
                                output = "You Guessed Correct!✅"
                                gameEnded = true
                            }
                            if (clickCount >= 10 && !gameEnded) {
                                output =
                                    "You used all 10 of your chances to guess😔\nThe number was $question"
                                gameEnded = true
                            }
                        },
                        enabled = clickCount < 10 && !gameEnded,
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .height(56.dp)
                            .fillMaxWidth(0.8f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = Color.Gray,
                            disabledContainerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)
                        )
                    ) {
                        Text(text = "Guess", fontSize = 20.sp)
                    }
                    Spacer(Modifier.height(80.dp))
                    Text(
                        text = output,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = TextStyle(lineHeight = 30.sp)
                    )
                }
            }

            if (gameEnded) {
                IconButton(
                    onClick = { restartGame() },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 64.dp, end = 16.dp) // Adjusted padding
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Restart Game",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(200.dp)
                            .background(MaterialTheme.colorScheme.tertiary)
                    )
                }
            }
        }
    }

    @Preview(
        name = "Preview",
        device = "id:pixel_7_pro",
        showBackground = true,
        showSystemUi = true
    )
    @Composable
    fun Preview() {
        NumberguessinggameTheme {
            AppUI()
        }
    }
}
