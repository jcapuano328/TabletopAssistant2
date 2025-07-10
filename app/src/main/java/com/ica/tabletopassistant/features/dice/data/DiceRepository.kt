package com.ica.tabletopassistant.features.dice.data

import androidx.datastore.core.DataStore
import com.ica.tabletopassistant.data.dice.DiceFeatureConfig
import com.ica.tabletopassistant.data.dice.Die
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiceRepository @Inject constructor(
    private val dataStore: DataStore<DiceFeatureConfig>
) {
    val configFlow: Flow<DiceFeatureConfig> = dataStore.data

    suspend fun setIsEnabled(isEnabled: Boolean) {
        dataStore.updateData {
            it.toBuilder()
                .setIsEnabled(isEnabled)
                .build()
        }
    }

    suspend fun setIsOneBased(isOneBased: Boolean) {
        dataStore.updateData {
            it.toBuilder()
                .setIsOneBased(isOneBased)
                .build()
        }
    }

    suspend fun setDice(dice: List<Die>) {
        dataStore.updateData { config ->
            config.toBuilder()
                .clearDice()
                .addAllDice(dice)
                .build()
        }
    }

    suspend fun updateDie(index: Int, newValue: Int) {
        dataStore.updateData { config ->
            val diceList = config.diceList.toMutableList()
            if (index in diceList.indices) {
                val oldDie = diceList[index]
                diceList[index] = oldDie.toBuilder()
                    .setCurrentValue(newValue)
                    .build()
            }
            config.toBuilder().clearDice().addAllDice(diceList).build()
        }
    }

    suspend fun addDie(sides: Int, dieColor: String, dotColor: String) {
        dataStore.updateData { config ->
            val newDie = Die.newBuilder()
                .setSides(sides)
                .setDieColor(dieColor)
                .setDotColor(dotColor)
                .setCurrentValue(1)
                .build()

            config.toBuilder()
                .addDice(newDie)
                .build()
        }
    }
}
