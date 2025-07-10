package com.ica.tabletopassistant.features.odds.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ica.tabletopassistant.features.odds.data.OddsRepository
import com.ica.tabletopassistant.data.odds.OddsFeatureConfig
import com.ica.tabletopassistant.features.dice.settings.DiceSettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OddsSettingsUiState(
    val isEnabled: Boolean = false,
    val isRounded: Boolean = false,
    val roundingMode : Int = 0,
    val attack: Float = 1f,
    val defend: Float = 1f
)

@HiltViewModel
class OddsSettingsViewModel @Inject constructor(
    private val repository: OddsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(OddsSettingsUiState())
    val uiState: StateFlow<OddsSettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.configFlow.collect { config ->
                _uiState.value = OddsSettingsUiState(
                    isEnabled = config.isEnabled,
                    isRounded = config.isRounded,
                    roundingMode = config.roundingMode,
                    attack = config.attack,
                    defend = config.defend
                )
            }
        }
    }

    fun setIsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.setIsEnabled(enabled)
        }
    }

    fun setIsRounded(rounded: Boolean) {
        viewModelScope.launch {
            repository.setIsRounded(rounded)
        }
    }

    fun setRoundingMode(mode: Int) {
        viewModelScope.launch {
            repository.setRoundingMode(mode)
        }
    }

    fun setAttack(value: Float) {
        viewModelScope.launch {
            repository.setAttackValue(value)
        }
    }

    fun setDefend(value: Float) {
        viewModelScope.launch {
            repository.setDefendValue(value)
        }
    }
}
