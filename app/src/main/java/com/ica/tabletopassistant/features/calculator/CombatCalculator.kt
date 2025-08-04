package com.ica.tabletopassistant.features.calculator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.ica.tabletopassistant.R

/**
 * Jetpack Compose implementation of the specialised “combat calculator”.
 *
 *  ┌───────┐
 *  │  LOGIC│ – The component exposes callbacks when ATT / DEF are committed so state can be hoisted.
 *  └───────┘
 *  The public API is a single composable plus a tiny data‑class representing an "entry" event.
 */

@Composable
fun CombatCalculator(
    onAttackCommitted: (Float) -> Unit,
    onDefendCommitted: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    /* ───────────────────────── STATE ───────────────────────── */
    var display by remember { mutableStateOf("0") }
    var overwrite by remember { mutableStateOf(false) }
    var operation by remember { mutableStateOf<Op?>(null) }
    var lhs by remember { mutableStateOf(0f) }

    /* ──────────────────────── HELPERS ──────────────────────── */
    fun performOp(op: Op, a: Float, b: Float): Float = when (op) {
        Op.Add      -> a + b
        Op.Subtract -> a - b
        Op.Multiply -> a * b
        Op.Divide   -> a / b
    }

    fun commitNumber(n: String) {
        if (display == "0" || overwrite) {
            display = ""
            overwrite = false
        }
        display += n
    }
    fun commitFloat(floatOp: (Float) -> Unit) {
        val value = display.toFloatOrNull() ?: 0f
        floatOp(value)
        // reset
        display = "0"; operation = null; lhs = 0f; overwrite = true
    }
    fun handleOperationInput(op: Op) {
        val current = (display.toFloatOrNull() ?: 0f)
        if (operation == null) {
            lhs = current
        } else {
            lhs = performOp(operation!!, lhs, current)
            display = lhs.toString()
        }
        operation = op
        overwrite = true
    }

    fun processKey(key: Any) {
        when (key) {
            is Int -> {
                if (display == "0") display = ""
                display += key.toString()
            }
            "calc-bksp" -> {
                display = display.dropLast(1).ifEmpty { "0" }
            }
            "calc-att" -> {
                commitFloat(onAttackCommitted)
            }
            "calc-def" -> {
                commitFloat(onDefendCommitted)
            }
            "calc-clear" -> {
                display = "0"; lhs = 0f; operation = null
            }
            "calc-divide" -> {
                handleOperationInput(Op.Divide)
            }
            "calc-multiply" -> {
                handleOperationInput(Op.Multiply)
            }
            "calc-subtract" -> {
                handleOperationInput(Op.Subtract)
            }
            "calc-add" -> {
                handleOperationInput(Op.Add)
            }
            "calc-plus-minus" -> {
                display = if (display.startsWith("-")) display.drop(1) else "-$display"
            }
            "calc-point" -> {
                if (!display.contains('.')) commitNumber(".")
            }
            "calc-equals" -> {
                val rhs = display.toFloatOrNull() ?: return
                val result = operation?.let { performOp(it, lhs, rhs) } ?: rhs
                display = result.toString()
                operation = null; lhs = 0f; overwrite = true
            }
            else -> {
                // key must be a digit of form calc-<digit>
                // let's extract the digit
                val digit = key.toString().substringAfterLast('-')
                commitNumber(digit)
            }

        }
    }

    /* ────────────────────────── UI ─────────────────────────── */

    /*
    4 cols by 6 rows
        row 1
            1/3 col, 2 2/3 col, 1 col
            op sym, display, backspace
        row 2
            1 col, 1, col, 1, col, 1 col
            att, def, c, divide
        row 3
            1 col, 1, col, 1, col, 1 col
            7, 8, 9, multiply
        row 4
            1 col, 1, col, 1, col, 1 col
            4, 5, 6, subtract
        row 5
            1 col, 1, col, 1, col, 1 col
            1, 2, 3, add
        row 6
            1 col, 1, col, 1, col, 1 col
            +/-, 0, ., =

    */
    Column(
        modifier = modifier
            .fillMaxSize()
            //.padding(4.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // First row: 3 cells with custom widths
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1/3f) // 1/3 standard
                    .fillMaxHeight()
                    .background(Color.Red)
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = operation?.symbol ?: "",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .weight(8/3f) // 2 2/3 standard
                    .fillMaxHeight()
                ,
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = display,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f) // 1 standard
                    .fillMaxHeight()
                ,
                contentAlignment = Alignment.Center
            ) {
                val key = CalculatorDisplay.keyForCell(0, 4)
                CalculatorIconButton(
                    CalculatorDisplay.iconForKey(key),
                    onClick = {
                        processKey(key)
                    }
                )
            }
        }

        // Rows 2–6: 4 standard
        for (row in 1..5) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (col in 1..4) {
                    val key = CalculatorDisplay.keyForCell(row, col)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        CalculatorIconButton(
                            CalculatorDisplay.iconForKey(key),
                            onClick = {
                                processKey(key)
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

/* ───────────────────────── BUTTONS ───────────────────────── */
@Composable
fun CalculatorIconButton(
    imageResId: Int, // drawable resource ID
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    width: Dp? = null,
    height: Dp? = null,
    contentDescription: String? = null,
    scale: Float = 1f,
    resizeMode: ContentScale = ContentScale.FillBounds // equivalent to 'contain'
) {
    var layoutSize by remember { mutableStateOf(IntSize.Zero) }

    val finalWidth = width ?: (layoutSize.width.takeIf { it != 0 }?.dp?.times(scale) ?: 32.dp)
    val finalHeight = height ?: (layoutSize.height.takeIf { it != 0 }?.dp?.times(scale) ?: 32.dp)

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                layoutSize = coordinates.size
            }
            .clickable { onClick() }
            .size(width = finalWidth, height = finalHeight)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = contentDescription,
            contentScale = resizeMode,
            modifier = Modifier.fillMaxSize()
        )
    }
}

/* ────────────────────────── DATA ─────────────────────────── */
private enum class Op(val symbol: String) {
    Add("+"), Subtract("-"), Multiply("×"), Divide("÷")
}

object CalculatorDisplay {

    fun keyForCell(row: Int, column: Int) : String {
        val key = when {
            row == 0 && column == 4 -> "calc-bksp"

            row == 1 && column == 1 -> "calc-att"
            row == 1 && column == 2 -> "calc-def"
            row == 1 && column == 3 -> "calc-clear"
            row == 1 && column == 4 -> "calc-divide"

            row == 2 && column == 1 -> "calc-7"
            row == 2 && column == 2 -> "calc-8"
            row == 2 && column == 3 -> "calc-9"
            row == 2 && column == 4 -> "calc-multiply"

            row == 3 && column == 1 -> "calc-4"
            row == 3 && column == 2 -> "calc-5"
            row == 3 && column == 3 -> "calc-6"
            row == 3 && column == 4 -> "calc-subtract"

            row == 4 && column == 1 -> "calc-1"
            row == 4 && column == 2 -> "calc-2"
            row == 4 && column == 3 -> "calc-3"
            row == 4 && column == 4 -> "calc-add"

            row == 5 && column == 1 -> "calc-plus-minus"
            row == 5 && column == 2 -> "calc-0"
            row == 5 && column == 3 -> "calc-point"
            row == 5 && column == 4 -> "calc-equals"

            else -> ""
        }

        return key
    }

    fun iconForCell(row: Int, column: Int) : Int {
        val key = keyForCell(row, column)
        return iconForKey(key)
    }

    fun iconForKey(result: String) : Int {
        if (result == "calc-0") return R.drawable.calc_0
        if (result == "calc-1") return R.drawable.calc_1
        if (result == "calc-2") return R.drawable.calc_2
        if (result == "calc-3") return R.drawable.calc_3
        if (result == "calc-4") return R.drawable.calc_4
        if (result == "calc-5") return R.drawable.calc_5
        if (result == "calc-6") return R.drawable.calc_6
        if (result == "calc-7") return R.drawable.calc_7
        if (result == "calc-8") return R.drawable.calc_8
        if (result == "calc-9") return R.drawable.calc_9
        if (result == "calc-add") return R.drawable.calc_add
        if (result == "calc-subtract") return R.drawable.calc_subtract
        if (result == "calc-multiply") return R.drawable.calc_multiply
        if (result == "calc-divide") return R.drawable.calc_divide
        if (result == "calc-att") return R.drawable.calc_att
        if (result == "calc-bksp") return R.drawable.calc_backspace
        if (result == "calc-clear") return R.drawable.calc_clear
        if (result == "calc-equals") return R.drawable.calc_equals
        if (result == "calc-def") return R.drawable.calc_def
        if (result == "calc-plus-minus") return R.drawable.calc_plus_minus
        if (result == "calc-point") return R.drawable.calc_point
        return 0
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCombatCalculator() {
    CombatCalculator(
        onAttackCommitted = { value ->
            println("Attack value: $value")
        },
        onDefendCommitted = { value ->
            println("Defend value: $value")
        }
    )
}