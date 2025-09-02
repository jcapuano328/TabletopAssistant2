package com.ica.tabletopassistant.features.odds.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ica.tabletopassistant.features.odds.data.OddsRepository
import com.ica.tabletopassistant.data.odds.OddsFeatureConfig
import com.ica.tabletopassistant.features.dice.settings.DiceSettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round
import javax.inject.Inject

data class OddsFeatureUiState(
    val isEnabled: Boolean = false,
    val isRounded: Boolean = false,
    val roundingMode : Int = 0,
    val attack: String = "1",
    val defend: String = "1",
    val odds: String = "1:1"
)

@HiltViewModel
class OddsFeatureViewModel @Inject constructor(
    private val repository: OddsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(OddsFeatureUiState())
    val uiState: StateFlow<OddsFeatureUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.configFlow.collect { config ->
                _uiState.value = OddsFeatureUiState(
                    isEnabled = config.isEnabled,
                    isRounded = config.isRounded,
                    roundingMode = config.roundingMode,
                    attack = config.attack.toString(),
                    defend = config.defend.toString()
                )
            }
            calcOdds()
        }
    }

    fun setAttack(value: String) {
        viewModelScope.launch {
            repository.setAttackValue(value)
            calcOdds()
        }
    }

    fun setDefend(value: String) {
        viewModelScope.launch {
            repository.setDefendValue(value)
            calcOdds()
        }
    }

    private fun calcOdds() {
        val attackStr = _uiState.value.attack.toFloatOrNull() ?: 1f
        val defendStr = _uiState.value.defend.toFloatOrNull() ?: 1f
        val oddsString = com.ica.tabletopassistant.util.MathUtils().calcOdds(attackStr, defendStr, _uiState.value.isRounded, _uiState.value.roundingMode)
        _uiState.value = _uiState.value.copy(odds = oddsString)
    }
}
