package com.ica.tabletopassistant.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun RollableDie(
    modifier: Modifier = Modifier,
    dieNumber: Int,
    onDieClicked: (Int) -> Unit,
    sides: Int,
    dieColor: Color,
    dotColor: Color,
    dieValue: Int,
    rollTrigger: Int,
    onAnimationComplete: () -> Unit = {}
) {
    var face by remember { mutableStateOf(dieValue) }

    // Rotation and scale animatables
    val rotation = remember { Animatable(0f) }
    val scale = remember { Animatable(1f) }

    // Trigger roll animation
    LaunchedEffect(rollTrigger) {
        if (rollTrigger > 0) {
            // Launch wobble in parallel
            launch {
                // Small oscillating rotation
                repeat(6) {
                    rotation.animateTo(
                        targetValue = if (it % 2 == 0) 10f else -10f,
                        animationSpec = tween(durationMillis = 50, easing = LinearEasing)
                    )
                }
                // Reset to zero
                rotation.animateTo(0f)
            }
            launch {
                // Subtle squash/stretch
                repeat(6) {
                    scale.animateTo(
                        targetValue = if (it % 2 == 0) 1.1f else 0.9f,
                        animationSpec = tween(durationMillis = 50, easing = LinearEasing)
                    )
                }
                // Reset to normal
                scale.animateTo(1f)
            }

            // Cycle through faces quickly
            repeat(8) { i ->
                face = (1..sides).random()
                delay(50L + i * 10L) // slow down slightly
            }

            face = dieValue
            onAnimationComplete()
        }
    }
    Box(
        modifier = modifier
            .size(64.dp)
            .graphicsLayer {
                rotationZ = rotation.value
                scaleX = scale.value
                scaleY = scale.value
            }
    ) {
        Die(
            dieNumber = dieNumber,
            onDieClicked = onDieClicked,
            sides = sides,
            dieColor = dieColor,
            dotColor = dotColor,
            dieValue = face
        )
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewRollableDie() {
    var value1 by remember { mutableStateOf(6) }
    var value2 by remember { mutableStateOf(8) }
    var value3 by remember { mutableStateOf(10) }
    var rollTrigger by remember { mutableStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.size(65.dp).padding(8.dp)) {
                RollableDie(
                    //modifier = Modifier.size(65.dp),
                    dieNumber = 1,
                    onDieClicked = { die -> value1 = die },
                    sides = 6,
                    dieColor = Color.Blue,
                    dotColor = Color.White,
                    dieValue = value1,
                    rollTrigger = rollTrigger
                )
            }

            Box(modifier = Modifier.size(65.dp).padding(8.dp)) {
                RollableDie(
                    //modifier = Modifier.size(65.dp),
                    dieNumber = 2,
                    onDieClicked = { die -> value2 = die },
                    sides = 8,
                    dieColor = Color.Red,
                    dotColor = Color.White,
                    dieValue = value2,
                    rollTrigger = rollTrigger
                )
            }

            Box(modifier = Modifier.size(65.dp).padding(8.dp)) {
                RollableDie(
                    //modifier = Modifier.size(65.dp),
                    dieNumber = 3,
                    onDieClicked = { die -> value3 = die },
                    sides = 10,
                    dieColor = Color.Green,
                    dotColor = Color.White,
                    dieValue = value3,
                    rollTrigger = rollTrigger
                )
            }
        }


        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                value1 = (1..6).random()
                value2 = (1..8).random()
                value3 = (1..10).random()
                rollTrigger++
                println("Rolling dice: $value1, $value2, $value3")
            }
        ) {
            Text("Roll")
        }
    }
}