package com.ica.tabletopassistant.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InputNumeric(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Enter Value"
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .defaultMinSize(minWidth = 28.dp, minHeight = 28.dp) // Set min size
                .size(28.dp)
                .weight(1f)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(1.dp))
            ,
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                //label = { Text(label, textAlign = TextAlign.Center) },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth() // TextField fills the Box
                    //.border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(1.dp))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInputNumeric() {
    var value by remember { mutableStateOf("5.5") }
    InputNumeric(
        modifier = Modifier
            .fillMaxWidth()
        ,
        value = value,
        onValueChange = { value = it }
    )
}