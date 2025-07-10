package com.ica.tabletopassistant.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.ica.tabletopassistant.util.DieConfig

@Composable
fun DiceSet(
    dieConfigs: List<DieConfig>,
    dieValues: List<Int>,
    onDieClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in dieConfigs.indices) {
            //println("Die $i has value ${dieValues[i]}")
            Die(dieNumber = i,
                modifier = Modifier
                    .padding(1.dp)
                    .size(40.dp)
                ,
                onDieClicked = onDieClicked,
                sides = 6,
                dieColor = dieConfigs[i].backgroundColor,
                dotColor = dieConfigs[i].dotColor,
                dieValue = dieValues[i]
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDiceSet() {
    var dieValues1 by remember { mutableStateOf(List(2) { 6 }) }
    val dieConfigs1 = listOf(
        DieConfig(backgroundColor = Color.Red, dotColor = Color.White),
        DieConfig(backgroundColor = Color.White, dotColor = Color.Black)
    )

    var dieValues2 by remember { mutableStateOf(List(3) { 6 }) }
    val dieConfigs2 = listOf(
        DieConfig(backgroundColor = Color.Blue, dotColor = Color.White),
        DieConfig(backgroundColor = Color.Yellow, dotColor = Color.Black),
        DieConfig(backgroundColor = Color.Green, dotColor = Color.Black),
    )

    var dieValues3 by remember { mutableStateOf(List(2) { 6 }) }
    val dieConfigs3 = listOf(
        DieConfig(backgroundColor = Color.Black, dotColor = Color.Red),
        DieConfig(backgroundColor = Color.Black, dotColor = Color.White)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
        ,horizontalArrangement = Arrangement.spacedBy(8.dp)
        ,verticalAlignment = Alignment.Top

    ) {
        DiceSet(
            modifier = Modifier
                .weight(2/7f)
                .height(40.dp)
            ,
            dieConfigs = dieConfigs1,
            dieValues = dieValues1,
            onDieClicked = { die ->
                dieValues1 = dieValues1.mapIndexed { index, value ->
                    if (index == die) (1..6).random() else value
                }
            }
        )
        DiceSet(
            modifier = Modifier
                .weight(3/7f)
                .height(40.dp)
            ,
            dieConfigs = dieConfigs2,
            dieValues = dieValues2,
            onDieClicked = { die ->
                dieValues2 = dieValues2.mapIndexed { index, value ->
                    if (index == die) (1..6).random() else value
                }
            }
        )

        DiceSet(
            modifier = Modifier
                .weight(2/7f)
                .height(40.dp)
            ,
            dieConfigs = dieConfigs3,
            dieValues = dieValues3,
            onDieClicked = { die ->
                dieValues3 = dieValues3.mapIndexed { index, value ->
                    if (index == die) (1..6).random() else value
                }
            }
        )

    }
}