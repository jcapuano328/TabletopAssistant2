package com.ica.tabletopassistant.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


/* ---------- 6-sided (d6) renderer ---------- */
@Composable
fun DieRendererD6(
    value: Int,            // The face value (1-6)
    dieColor: Color,
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
            color = dieColor,
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
fun PreviewDieRendererD6() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        DieRendererD6(
            dieColor = Color.White,
            dotColor = Color.Black,
            value = 6
        )
    }
}

/* ---------- 8-sided (d8) renderer ---------- */
/*
@Composable
fun DieRendererD8(
    value: Int,
    dieColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier) {
        val sizePx = with(LocalDensity.current) {
            min(maxWidth.toPx(), maxHeight.toPx())
        }
        Canvas(Modifier.matchParentSize()) {
            val radius = sizePx * 0.45f
            val center = Offset(size.width / 2, size.height / 2)
            val border = 2.dp.toPx()

            // ♦ draw diamond (two equilateral triangles) – square rotated 45°
            val path = Path().apply {
                addRegularPolygon(center, radius, sides = 4, rotationRad = PI.toFloat() / 4)
            }
            drawPath(path, color = dieColor)
            drawPath(path, color = Color.Black, style = Stroke(border))

            // draw face value
            drawContext.canvas.nativeCanvas.apply {
                val textSize = sizePx * 0.45f
                val paint = android.graphics.Paint().apply {
                    isAntiAlias = true
                    color = textColor.toArgb()
                    textAlign = android.graphics.Paint.Align.CENTER
                    this.textSize = textSize
                    typeface = android.graphics.Typeface.DEFAULT_BOLD
                }
                drawText(
                    value.toString(),
                    center.x,
                    center.y + textSize / 3, // good optical centering
                    paint
                )
            }
        }
    }
}

@Composable
fun DieRendererD8(
    value: Int,
    dieColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier) {
        val sizePx = with(LocalDensity.current) {
            min(maxWidth.toPx(), maxHeight.toPx())
        }
        Canvas(Modifier.matchParentSize()) {
            val border = 2.dp.toPx()
            val radius = sizePx * 0.45f // controls size of triangle
            val center = Offset(size.width / 2, size.height / 2)

            // Points of an upward equilateral triangle
            val pointA = Offset(center.x, center.y - radius)
            val pointB = Offset(
                center.x - radius * cos(PI / 6).toFloat(),
                center.y + radius * sin(PI / 6).toFloat()
            )
            val pointC = Offset(
                center.x + radius * cos(PI / 6).toFloat(),
                center.y + radius * sin(PI / 6).toFloat()
            )

            val trianglePath = Path().apply {
                moveTo(pointA.x, pointA.y)
                lineTo(pointB.x, pointB.y)
                lineTo(pointC.x, pointC.y)
                close()
            }

            // Fill
            drawPath(trianglePath, color = dieColor)

            // Border
            drawPath(trianglePath, color = Color.Black, style = Stroke(border))

            // Face value
            drawContext.canvas.nativeCanvas.apply {
                val textSize = sizePx * 0.4f
                val paint = android.graphics.Paint().apply {
                    isAntiAlias = true
                    color = textColor.toArgb()
                    textAlign = android.graphics.Paint.Align.CENTER
                    this.textSize = textSize
                    typeface = android.graphics.Typeface.DEFAULT_BOLD
                }
                drawText(
                    value.toString(),
                    center.x,
                    center.y + textSize / 3,
                    paint
                )
            }
        }
    }
}

@Composable
fun DieRendererD8(
    value: Int,
    dieColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val border = 2.dp.toPx()
        val usableSize = size.minDimension - border   // subtract stroke to prevent clipping
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = usableSize / 2f

        // Equilateral triangle pointing up
        val angle = (Math.PI / 6).toFloat() // 30° for equilateral triangle orientation

        val pointA = Offset(center.x, center.y - radius) // Top vertex
        val pointB = Offset(
            center.x - radius * cos(angle),
            center.y + radius * sin(angle)
        )
        val pointC = Offset(
            center.x + radius * cos(angle),
            center.y + radius * sin(angle)
        )

        val triangle = Path().apply {
            moveTo(pointA.x, pointA.y)
            lineTo(pointB.x, pointB.y)
            lineTo(pointC.x, pointC.y)
            close()
        }

        // Draw filled triangle
        drawPath(triangle, color = dieColor)

        // Draw triangle border
        drawPath(triangle, color = Color.Black, style = Stroke(width = border))

        // Draw centered number
        drawContext.canvas.nativeCanvas.apply {
            val textSize = usableSize * 0.4f
            val paint = android.graphics.Paint().apply {
                isAntiAlias = true
                color = textColor.toArgb()
                textAlign = android.graphics.Paint.Align.CENTER
                this.textSize = textSize
                typeface = android.graphics.Typeface.DEFAULT_BOLD
            }
            drawText(
                value.toString(),
                center.x,
                center.y + textSize / 3, // optical vertical centering
                paint
            )
        }
    }
}
*/
@Composable
fun DieRendererD8(
    value: Int,
    dieColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val border = 2.dp.toPx()
        val usableSize = size.minDimension - border
        val center = Offset(size.width / 2, size.height / 2)
        val halfSize = usableSize / 2

        // Diamond points (square rotated 45°)
        val top = Offset(center.x, center.y - halfSize)
        val right = Offset(center.x + halfSize, center.y)
        val bottom = Offset(center.x, center.y + halfSize)
        val left = Offset(center.x - halfSize, center.y)

        val diamond = Path().apply {
            moveTo(top.x, top.y)
            lineTo(right.x, right.y)
            lineTo(bottom.x, bottom.y)
            lineTo(left.x, left.y)
            close()
        }

        // Draw diamond shape
        drawPath(diamond, color = dieColor)
        drawPath(diamond, color = Color.Black, style = Stroke(width = border))

        // Draw centered number
        drawContext.canvas.nativeCanvas.apply {
            val textSize = usableSize * 0.4f
            val paint = android.graphics.Paint().apply {
                isAntiAlias = true
                color = textColor.toArgb()
                textAlign = android.graphics.Paint.Align.CENTER
                this.textSize = textSize
                typeface = android.graphics.Typeface.DEFAULT_BOLD
            }
            drawText(
                value.toString(),
                center.x,
                center.y + textSize / 3, // Optical centering tweak
                paint
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDieRendererD8() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        DieRendererD8(
            dieColor = Color.White,
            textColor = Color.Black,
            value = 8
        )
    }
}

/* ---------- 10‑sided (d10) renderer ---------- */
@Composable
fun DieRendererD10(
    value: Int,
    dieColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier) {
        val sizePx = with(LocalDensity.current) {
            min(maxWidth.toPx(), maxHeight.toPx())
        }
        Canvas(Modifier.matchParentSize()) {
            val radius = sizePx * 0.48f
            val center = Offset(size.width / 2, size.height / 2)
            val border = 2.dp.toPx()

            // ⬟ draw decagon rotated a bit to get the usual “kite/diamond” silhouette
            val path = Path().apply {
                addRegularPolygon(center, radius, sides = 10, rotationRad = PI.toFloat() / 10)
            }
            drawPath(path, color = dieColor)
            drawPath(path, color = Color.Black, style = Stroke(border))

            // draw face value (d10 often prints 00–90 for ‑percentile dice; adapt if needed)
            drawContext.canvas.nativeCanvas.apply {
                val textSize = sizePx * 0.42f
                val paint = android.graphics.Paint().apply {
                    isAntiAlias = true
                    color = textColor.toArgb()
                    textAlign = android.graphics.Paint.Align.CENTER
                    this.textSize = textSize
                    typeface = android.graphics.Typeface.DEFAULT_BOLD
                }
                drawText(
                    value.toString(),
                    center.x,
                    center.y + textSize / 3,
                    paint
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDieRendererD10() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        DieRendererD10(
            dieColor = Color.White,
            textColor = Color.Black,
            value = 10
        )
    }
}


/* ---------- tiny geometry helper ---------- */
private fun Path.addRegularPolygon(
    center: Offset,
    radius: Float,
    sides: Int,
    rotationRad: Float = 0f
) {
    if (sides < 3) return
    reset()
    val angle = 2 * PI.toFloat() / sides
    moveTo(
        x = center.x + radius * cos(rotationRad),
        y = center.y + radius * sin(rotationRad)
    )
    for (i in 1 until sides) {
        val theta = rotationRad + angle * i
        lineTo(
            x = center.x + radius * cos(theta),
            y = center.y + radius * sin(theta)
        )
    }
    close()
}
