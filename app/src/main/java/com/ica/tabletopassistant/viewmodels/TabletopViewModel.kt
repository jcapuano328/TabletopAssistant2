package com.ica.tabletopassistant.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import com.ica.tabletopassistant.util.DieConfig
import com.ica.tabletopassistant.util.MathUtils

data class DiceSet(
    val dieConfigs: List<DieConfig>,
    val dieValues: MutableStateFlow<List<Int>>
)


class TabletopViewModel : ViewModel() {
    init {
        createDiceSets()
        setInitialDieValues()
    }

    private fun createDiceSets() {
    }
    private fun setInitialDieValues() {
    }

    suspend fun onFabClicked() {
        viewModelScope.launch {
            rollDice()
        }
    }

    private fun rollDice() {
        val random = MathUtils()

        // Generate random numbers from 1 to 6
        //_diceSetFire.value.dieValues.value = List(_diceSetFire.value.dieConfigs.size) { random.randomDie6() }
        //_diceSetLeader.value.dieValues.value = List(_diceSetLeader.value.dieConfigs.size) { random.randomDie6() }
        //_diceSetMorale.value.dieValues.value = List(_diceSetMorale.value.dieConfigs.size) { random.randomDie6() }
    }

    private fun updateResults() {
    }
}