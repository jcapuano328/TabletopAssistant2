package com.ica.tabletopassistant.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.ica.tabletopassistant.features.odds.data.OddsRepository
import com.ica.tabletopassistant.data.odds.OddsFeatureConfig

import com.ica.tabletopassistant.features.spinners.data.SpinnersRepository
import com.ica.tabletopassistant.data.spinners.SpinnersFeatureConfig

import com.ica.tabletopassistant.features.dice.data.DiceRepository
import com.ica.tabletopassistant.data.dice.DiceFeatureConfig


data class TabletopScreenUiState(
    val isOddsEnabled: Boolean = false,
    val isSpinnersEnabled: Boolean = false,
    val isDiceEnabled: Boolean = false
)

@HiltViewModel
class TabletopScreenViewModel @Inject constructor(
    private val oddsRepository: OddsRepository,
    private val spinnersRepository: SpinnersRepository,
    private val diceRepository: DiceRepository
) : ViewModel() {

    val uiState: StateFlow<TabletopScreenUiState> = combine(
        diceRepository.configFlow,
        oddsRepository.configFlow,
        spinnersRepository.configFlow
    ) { diceConfig, oddsConfig, spinnersConfig ->
        TabletopScreenUiState(
            isDiceEnabled = diceConfig.isEnabled,
            isOddsEnabled = oddsConfig.isEnabled,
            isSpinnersEnabled = spinnersConfig.isEnabled
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TabletopScreenUiState()
    )
}
