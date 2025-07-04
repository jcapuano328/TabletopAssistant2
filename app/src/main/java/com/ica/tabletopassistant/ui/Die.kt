package com.ica.tabletopassistant.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Die(
    dieNumber: Int,
    onDieClicked: (Int) -> Unit,
    backgroundColor: Color,
    dotColor: Color,
    modifier: Modifier = Modifier,
    dieValue: Int
) {
    Box(
        modifier = modifier
            //.sizeIn(minWidth = 64.dp, minHeight = 64.dp)
            .aspectRatio(1f)
            //.border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(1.dp))
            //.background(backgroundColor, shape = RoundedCornerShape(1.dp))
            .padding(1.dp),
        contentAlignment = Alignment.Center
    ) {
        DieRenderer(
            backgroundColor = backgroundColor,
            dotColor = dotColor,
            value = dieValue,
            modifier = modifier
                //.border(width = 1.dp, color = Color.Black)
                .clickable { onDieClicked(dieNumber) }
        )
    }
}

@Composable
fun DieRenderer(
    value: Int,            // The face value (1-6)
    backgroundColor: Color,
    dotColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val size = size.minDimension
        //println("DieRenderer: size = $size")
        val cornerRadius = size * 0.2f // Adjust for rounded corners
        val borderWidth = 2.dp.toPx() // Border thickness in pixels
        val dotRadius = size * 0.12f // Adjust dot size proportionally
        val positions = mapOf(
            1 to listOf(Offset(size / 2, size / 2)),
            2 to listOf(Offset(size * 0.25f, size * 0.25f), Offset(size * 0.75f, size * 0.75f)),
            3 to listOf(Offset(size * 0.25f, size * 0.25f), Offset(size * 0.5f, size * 0.5f), Offset(size * 0.75f, size * 0.75f)),
            4 to listOf(Offset(size * 0.25f, size * 0.25f), Offset(size * 0.75f, size * 0.25f),
                Offset(size * 0.25f, size * 0.75f), Offset(size * 0.75f, size * 0.75f)),
            5 to listOf(Offset(size * 0.25f, size * 0.25f), Offset(size * 0.75f, size * 0.25f),
                Offset(size * 0.5f, size * 0.5f),
                Offset(size * 0.25f, size * 0.75f), Offset(size * 0.75f, size * 0.75f)),
            6 to listOf(Offset(size * 0.25f, size * 0.2f), Offset(size * 0.75f, size * 0.2f),
                Offset(size * 0.25f, size * 0.5f), Offset(size * 0.75f, size * 0.5f),
                Offset(size * 0.25f, size * 0.8f), Offset(size * 0.75f, size * 0.8f))
        )

        //println("DieRenderer: value = $value, size = $size, dotRadius = $dotRadius")

        drawRoundRect(
            color = backgroundColor,
            size = Size(size, size),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
        )

        // Draw the border as a slightly smaller rounded rectangle
        drawRoundRect(
            color = Color.Black, // Border color
            size = Size(size - borderWidth, size - borderWidth), // Shrink slightly to fit
            topLeft = Offset(borderWidth / 2, borderWidth / 2), // Offset to center it
            cornerRadius = CornerRadius(cornerRadius, cornerRadius),
            style = Stroke(width = borderWidth) // Draw only the stroke (border)
        )

        positions[value]?.forEach { pos ->
            drawCircle(color = dotColor, radius = dotRadius, center = pos)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDie() {
    var value by remember { mutableStateOf(6) }
    Die(
        modifier = Modifier.size(40.dp),
        dieNumber = 1,
        onDieClicked = { die -> value = die },
        backgroundColor = Color.White,
        dotColor = Color.Black,
        dieValue = value
    )
}