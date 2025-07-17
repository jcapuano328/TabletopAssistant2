package com.ica.tabletopassistant.features.spinners.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ica.tabletopassistant.features.spinners.data.SpinnersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SpinnersFeatureUiState(
    val isEnabled: Boolean = false,
    val number: Int = 0,
    val followDice: Boolean = false,
    val calcDifference: Boolean = false,
    val showCalculator: Boolean = false,
    val values: List<Int> = emptyList(),
    val difference: Int = 0
)

@HiltViewModel
class SpinnersFeatureViewModel @Inject constructor(
    private val repository: SpinnersRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SpinnersFeatureUiState())
    val uiState: StateFlow<SpinnersFeatureUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.configFlow.collect { config ->
                _uiState.value = SpinnersFeatureUiState(
                    isEnabled = config.isEnabled,
                    number = config.number,
                    followDice = config.followDice,
                    calcDifference = config.calcDifference,
                    showCalculator = config.showCalculator,
                    values = config.valuesList
                )
                initializeValues()
                calcDifference()
            }
        }
    }

    fun updateValueAt(index: Int, newValue: Int) {
        val currentValues = _uiState.value.values.toMutableList()
        currentValues[index] = newValue
        updateValues(currentValues)
    }

    fun updateValues(values: List<Int>) {
        viewModelScope.launch {
            repository.setValues(values)
            _uiState.value = _uiState.value.copy(values = values)
            calcDifference()
        }
    }


    private fun initializeValues() {
        val currentValues = _uiState.value.values.toMutableList()
        val newValues = mutableListOf<Int>()

        // Copy existing values up to the new number
        for (i in 0 until _uiState.value.number) {
            newValues.add(currentValues.getOrNull(i) ?: 0) // Default to 0 if no existing value
        }

        //_uiState.value = _uiState.value.copy(values = newValues)
        updateValues(newValues)
    }

    private fun calcDifference() {
        if (_uiState.value.calcDifference && _uiState.value.number > 1)
            _uiState.value = _uiState.value.copy(difference = _uiState.value.values[0] - _uiState.value.values[1])
    }
}
