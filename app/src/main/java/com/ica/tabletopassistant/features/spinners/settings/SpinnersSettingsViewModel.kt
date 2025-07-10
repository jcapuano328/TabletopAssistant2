package com.ica.tabletopassistant.features.spinners.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ica.tabletopassistant.features.spinners.data.SpinnersRepository
import com.ica.tabletopassistant.data.spinners.SpinnersFeatureConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SpinnersSettingsUiState(
    val isEnabled: Boolean = false,
    val number: Int = 0,
    val followDice: Boolean = false,
    val calcDifference: Boolean = false,
    val showCalculator: Boolean = false,
    val values: List<Int> = emptyList()
)

@HiltViewModel
class SpinnersSettingsViewModel @Inject constructor(
    private val repository: SpinnersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SpinnersSettingsUiState())
    val uiState: StateFlow<SpinnersSettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.configFlow.collect { config ->
                _uiState.value = SpinnersSettingsUiState(
                    isEnabled = config.isEnabled,
                    number = config.number,
                    followDice = config.followDice,
                    calcDifference = config.calcDifference,
                    showCalculator = config.showCalculator,
                    values = config.valuesList
                )
            }
        }
    }


    fun setIsEnabled(enabled: Boolean) {
        viewModelScope.launch { repository.setIsEnabled(enabled) }
    }

    fun setNumber(number: Int) {
        viewModelScope.launch { repository.setNumber(number) }
    }

    fun setFollowDice(followDice: Boolean) {
        viewModelScope.launch { repository.setFollowDice(followDice) }
    }

    fun setCalcDifference(calcDifference: Boolean) {
        viewModelScope.launch { repository.setCalcDifference(calcDifference) }
    }

    fun setShowCalculator(showCalculator: Boolean) {
        viewModelScope.launch { repository.setShowCalculator(showCalculator) }
    }

    fun updateValueAt(index: Int, value: Int) {
        val currentValues = _uiState.value.values.toMutableList()
        if (index in currentValues.indices) {
            currentValues[index] = value
            viewModelScope.launch { repository.setValues(currentValues) }
        }
    }

    fun resetValuesToZero() {
        val newValues = List(_uiState.value.number) { 0 }
        viewModelScope.launch { repository.setValues(newValues) }
    }
}
