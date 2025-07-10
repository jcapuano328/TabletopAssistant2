package com.ica.tabletopassistant.features.odds.data

import androidx.datastore.core.DataStore
import com.ica.tabletopassistant.data.odds.OddsFeatureConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OddsRepository @Inject constructor(
    private val dataStore: DataStore<OddsFeatureConfig>
) {
    val configFlow: Flow<OddsFeatureConfig> = dataStore.data

    suspend fun setIsEnabled(isEnabled: Boolean) {
        dataStore.updateData { config ->
            config.toBuilder()
                .setIsEnabled(isEnabled)
                .build()
        }
    }

    suspend fun setIsRounded(isRounded: Boolean) {
        dataStore.updateData { config ->
            config.toBuilder()
                .setIsRounded(isRounded)
                .build()
        }
    }

    suspend fun setRoundingMode(mode: Int) {
        dataStore.updateData { config ->
            config.toBuilder()
                .setRoundingMode(mode)
                .build()
        }
    }

    suspend fun setAttackValue(value: Float) {
        dataStore.updateData { config ->
            config.toBuilder()
                .setAttack(value)
                .build()
        }
    }

    suspend fun setDefendValue(value: Float) {
        dataStore.updateData { config ->
            config.toBuilder()
                .setDefend(value)
                .build()
        }
    }

    suspend fun setBothValues(attack: Float, defend: Float) {
        dataStore.updateData { config ->
            config.toBuilder()
                .setAttack(attack)
                .setDefend(defend)
                .build()
        }
    }
}
