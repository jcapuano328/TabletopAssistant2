package com.ica.tabletopassistant.features.dice.data

import androidx.datastore.core.Serializer
import com.ica.tabletopassistant.data.dice.DiceFeatureConfig
import com.ica.tabletopassistant.data.dice.Die
import java.io.InputStream
import java.io.OutputStream

object DiceSerializer : Serializer<DiceFeatureConfig> {
    //override val defaultValue: DiceFeatureConfig = DiceFeatureConfig.getDefaultInstance()
    override val defaultValue: DiceFeatureConfig = DiceFeatureConfig.newBuilder()
        .setIsEnabled(true) // custom default
        .setIsOneBased(false)
        .addDice(
            Die.newBuilder()
                .setSides(6)
                .setDieColor("red")
                .setDotColor("white")
                .setCurrentValue(1)
                .setBreakAfter(false)
                .build()
        )
        .addDice(
            Die.newBuilder()
                .setSides(6)
                .setDieColor("white")
                .setDotColor("black")
                .setCurrentValue(1)
                .build()
        )
        .build()

    override suspend fun readFrom(input: InputStream): DiceFeatureConfig {
        return DiceFeatureConfig.parseFrom(input)
    }

    override suspend fun writeTo(t: DiceFeatureConfig, output: OutputStream) {
        t.writeTo(output)
    }


}
