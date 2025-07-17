package com.ica.tabletopassistant.features.dice.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ica.tabletopassistant.data.dice.Die
import com.ica.tabletopassistant.features.dice.data.DiceRepository
import com.ica.tabletopassistant.util.MathUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DiceFeatureUiState(
    val isEnabled: Boolean = false,
    val isOneBased: Boolean = true,
    val dice: List<Die> = emptyList()
)

@HiltViewModel
class DiceFeatureViewModel @Inject constructor(
    private val repository: DiceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiceFeatureUiState())
    val uiState: StateFlow<DiceFeatureUiState> = _uiState.asStateFlow()

    private val _fabEvent = MutableSharedFlow<Unit>()
    val fabEvent = _fabEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            repository.configFlow.collect { config ->
                _uiState.value = DiceFeatureUiState(
                    isEnabled = config.isEnabled,
                    isOneBased = config.isOneBased,
                    dice = config.diceList
                )
            }
        }
    }

    suspend fun onFabClicked() {
        println("DiceFeatureViewModel: FAB clicked")
        _fabEvent.emit(Unit)
        viewModelScope.launch {
            rollDice()
        }
    }

    fun updateDice(values: List<Int>) {
        val current = _uiState.value.dice.toMutableList()
        for (i in values.indices) {
            current[i] = current[i].toBuilder()
                .setCurrentValue(values[i])
                .build()
        }
        viewModelScope.launch {
            repository.setDice(current)
        }
    }

    fun updateDie(index: Int, value: Int) {
        val current = _uiState.value.dice.toMutableList()
        if (index in current.indices) {
            // Update the current value of the die
            current[index] = current[index].toBuilder()
                .setCurrentValue(value)
                .build()
            viewModelScope.launch {
                repository.setDice(current)
            }
        }
    }

    private fun rollDice() {
        val random = MathUtils()

        val newValues = _uiState.value.dice.map {
            when (it.sides) {
                6 -> random.randomDie6()
                8 -> random.randomDie8()
                10 -> random.randomDie10()
                else -> 1
            }
        }

        updateDice(newValues)
    }
}
