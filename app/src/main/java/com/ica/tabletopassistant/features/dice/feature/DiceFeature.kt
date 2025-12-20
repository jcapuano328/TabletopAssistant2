package com.ica.tabletopassistant.features.dice.feature

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration

import com.ica.tabletopassistant.data.dice.Die
import com.ica.tabletopassistant.R
import com.ica.tabletopassistant.ui.DieColorMap
import com.ica.tabletopassistant.util.*

@Composable
fun DiceFeature(
    modifier: Modifier = Modifier,
    onFabClickRequest: (suspend () -> Unit) -> Unit,
    viewModel: DiceFeatureViewModel = hiltViewModel()
) {
    var rollTrigger by remember { mutableStateOf(0) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        onFabClickRequest {
            rollTrigger++
            viewModel.onFabClicked()
        }
    }

    DiceFeatureContent(
        modifier = modifier,
        state = uiState,
        onUpdateDice = viewModel::updateDice,
        onUpdateDie = viewModel::updateDie,
        rollTrigger = rollTrigger
    )
}

@Composable
fun DiceFeatureContent(
    modifier: Modifier = Modifier,
    state: DiceFeatureUiState,
    onUpdateDice: (List<Int>) -> Unit = {},
    onUpdateDie: (Int, Int) -> Unit = { _, _ -> },
    rollTrigger: Int = 0
) {
    //Box(modifier = modifier.fillMaxSize()) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
// 1. Get the current configuration
        val configuration = LocalConfiguration.current

        // 2. Check the orientation
        val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
        val itemWidth = if (isPortrait) maxWidth / 6 else maxHeight / 3
        Image(
            painter = painterResource(id = R.drawable.tabletop),
            contentDescription = "Table top", // Provide a meaningful description
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // or ContentScale .Crop // Or other ContentScale options like Fit, FillBounds, etc.
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(2.dp)
        ) {
            val widgetsWithSpacers: List<GridItem> = buildList {
                for (die in state.dice) {
                    if (die.isSpacer) add(GridItem.Spacer)
                    else add(GridItem.Widget(die.sides, DieColorMap[die.dotColor] ?: Color.Gray, DieColorMap[die.dieColor] ?: Color.Gray, die.currentValue))
                }
            }

            val dieSize = itemWidth//65.dp
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = dieSize), // adjusts how many per row based on screen width
                modifier = Modifier
                    .fillMaxWidth() // assumes full width of screen
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(widgetsWithSpacers.size) { index ->
                    when (val item = widgetsWithSpacers[index]) {
                        is GridItem.Widget -> Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                //.background(item.dieColor)
                                .background(Color.Transparent)
                                .clip(RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            //com.ica.tabletopassistant.ui.RollableDie(
                            com.ica.tabletopassistant.ui.Die(
                                modifier = Modifier.fillMaxSize(),//.size(dieSize),
                                dieNumber = index,
                                onDieClicked = {
                                    // increment by one, rolling over to min
                                    val newValue = (item.value % item.sides) + 1
                                    onUpdateDie(index, newValue)
                                },
                                sides = item.sides,
                                dieColor = item.dieColor ?: Color.White,
                                dotColor = item.dotColor ?: Color.Black,
                                dieValue = item.value,
                                //rollTrigger = rollTrigger
                            )
                        }
                        GridItem.Spacer -> Box(
                            modifier = Modifier
                                .size(dieSize) // same size as others
                                .background(Color.Transparent)
                        )
                    }
                }
            }
        }
    }
}

sealed interface GridItem {
    data class Widget(val sides: Int, val dotColor: Color, val dieColor: Color, val value: Int): GridItem
    data object Spacer : GridItem
}


@Preview(showBackground = true)
@Composable
fun PreviewDiceFeature() {
    // Use remember to store the state locally within the preview
    var previewState by remember {
        mutableStateOf(
            DiceFeatureUiState(
                isEnabled = true,
                isOneBased = false,
                dice = listOf(
                    Die.newBuilder()
                        .setSides(6)
                        .setDieColor("red")
                        .setDotColor("white")
                        .setCurrentValue(1)
                        .build(),
                    Die.newBuilder()
                        .setSides(6)
                        .setDieColor("white")
                        .setDotColor("black")
                        .setCurrentValue(1)
                        .build(),
                    Die.newBuilder()
                        .setIsSpacer(true)
                        .build(),
                    Die.newBuilder()
                        .setSides(6)
                        .setDieColor("black")
                        .setDotColor("white")
                        .setCurrentValue(1)
                        .build(),
                    Die.newBuilder()
                        .setSides(6)
                        .setDieColor("black")
                        .setDotColor("red")
                        .setCurrentValue(1)
                        .build(),

                    Die.newBuilder()
                        .setSides(8)
                        .setDieColor("blue")
                        .setDotColor("white")
                        .setCurrentValue(1)
                        .build(),
                    Die.newBuilder()
                        .setSides(10)
                        .setDieColor("green")
                        .setDotColor("white")
                        .setCurrentValue(1)
                        .build(),
                    Die.newBuilder()
                        .setIsSpacer(true)
                        .build(),
                    Die.newBuilder()
                        .setSides(6)
                        .setDieColor("purple")
                        .setDotColor("white")
                        .setCurrentValue(1)
                        .build()
                )
            )
        )
    }

    Column (modifier = Modifier.fillMaxSize()) {
        DiceFeatureContent(
            modifier = Modifier.weight(10f),
            state = previewState,
            onUpdateDice = { values ->
                previewState = previewState.copy(dice = previewState.dice.mapIndexed { index, die ->
                    die.toBuilder()
                        .setCurrentValue(values[index])
                        .build()
                })
            },
            onUpdateDie = { i, value ->
                previewState = previewState.copy(dice = previewState.dice.mapIndexed { index, die ->
                    if (index != i) die else
                        die.toBuilder()
                            .setCurrentValue(value)
                            .build()
                })
            }
        )

        IconButton(modifier = Modifier.weight(1f),
            onClick = {
                //val newValues = previewState.dice.map { it.currentValue }
                val newValues = List(previewState.dice.size) { randomDie6() }
                previewState = previewState.copy(dice = previewState.dice.mapIndexed { index, die ->
                    die.toBuilder()
                        .setCurrentValue(newValues[index])
                        .build()
                })
        }) {
            Icon(Icons.Default.Casino, contentDescription = "Roll Dice")
        }
    }
}