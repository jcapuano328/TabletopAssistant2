package com.ica.tabletopassistant.features.dice.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ica.tabletopassistant.data.dice.DiceFeatureConfig
import com.ica.tabletopassistant.data.dice.Die
import com.ica.tabletopassistant.features.dice.data.DiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DiceSettingsUiState(
    val isEnabled: Boolean = false,
    val isOneBased: Boolean = true,
    val dice: List<Die> = emptyList()
)

@HiltViewModel
class DiceSettingsViewModel @Inject constructor(
    private val repository: DiceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiceSettingsUiState())
    val uiState: StateFlow<DiceSettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.configFlow.collect { config ->
                _uiState.value = DiceSettingsUiState(
                    isEnabled = config.isEnabled,
                    isOneBased = config.isOneBased,
                    dice = config.diceList
                )
            }
        }
    }

    fun setEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.setIsEnabled(enabled)
        }
    }

    fun setIsOneBased(oneBased: Boolean) {
        viewModelScope.launch {
            repository.setIsOneBased(oneBased)
        }
    }

    fun updateDie(index: Int, updated: Die) {
        val current = _uiState.value.dice.toMutableList()
        if (index in current.indices) {
            current[index] = updated
            viewModelScope.launch {
                repository.setDice(current)
            }
        }
    }

    fun addDie() {
        viewModelScope.launch {
            repository.addDie(
                sides = 6,
                dieColor = "white",
                dotColor = "black"
            )
        }
    }

    fun removeDie(index: Int) {
        val current = _uiState.value.dice.toMutableList()
        if (index in current.indices) {
            current.removeAt(index)
            viewModelScope.launch {
                repository.setDice(current)
            }
        }
    }
}
