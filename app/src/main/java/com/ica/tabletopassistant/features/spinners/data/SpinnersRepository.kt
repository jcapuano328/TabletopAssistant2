package com.ica.tabletopassistant.features.spinners.data

import androidx.datastore.core.DataStore
import com.ica.tabletopassistant.data.odds.OddsFeatureConfig
import com.ica.tabletopassistant.data.spinners.SpinnersFeatureConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpinnersRepository @Inject constructor(
    private val dataStore: DataStore<SpinnersFeatureConfig>
) {
    val configFlow: Flow<SpinnersFeatureConfig> = dataStore.data

    suspend fun setIsEnabled(enabled: Boolean) {
        dataStore.updateData { config ->
            config.toBuilder()
                .setIsEnabled(enabled)
                .build()
        }
    }

    suspend fun setNumber(number: Int) {
        dataStore.updateData { config ->
            config.toBuilder()
                .setNumber(number)
                .build()
        }
    }

    suspend fun setFollowDice(followDice: Boolean) {
        dataStore.updateData { config ->
            config.toBuilder()
                .setFollowDice(followDice)
                .build()
        }
    }

    suspend fun setCalcDifference(calcDifference: Boolean) {
        dataStore.updateData { config ->
            config.toBuilder()
                .setCalcDifference(calcDifference)
                .build()
        }
    }

    suspend fun setShowCalculator(showCalculator: Boolean) {
        dataStore.updateData { config ->
            config.toBuilder()
                .setShowCalculator(showCalculator)
                .build()
        }
    }

    suspend fun setValues(values: List<Int>) {
        dataStore.updateData { config ->
            config.toBuilder()
                .clearValues()
                .addAllValues(values)
                .build()
        }
    }

    suspend fun addValue(value: Int) {
        dataStore.updateData { config ->
            config.toBuilder()
                .addValues(value)
                .build()
        }
    }

    suspend fun removeValueAt(index: Int) {
        dataStore.updateData { config ->
            val current = config.valuesList.toMutableList()
            if (index in current.indices) {
                current.removeAt(index)
            }
            config.toBuilder()
                .clearValues()
                .addAllValues(current)
                .build()
        }
    }

    suspend fun reset() {
        dataStore.updateData {
            SpinnersFeatureConfig.getDefaultInstance()
        }
    }

}
