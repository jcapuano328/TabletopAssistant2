package com.ica.tabletopassistant.features.spinners.data

import androidx.datastore.core.Serializer
import com.ica.tabletopassistant.data.spinners.SpinnersFeatureConfig
import java.io.InputStream
import java.io.OutputStream

object SpinnersSerializer : Serializer<SpinnersFeatureConfig> {
    //override val defaultValue: SpinnersFeatureConfig = SpinnersFeatureConfig.getDefaultInstance()
    override val defaultValue: SpinnersFeatureConfig = SpinnersFeatureConfig.newBuilder()
        .setIsEnabled(false)
        .setNumber(1)
        .setFollowDice(false)
        .setCalcDifference(false)
        .setShowCalculator(false)
        .clearValues()
        .build()

    override suspend fun readFrom(input: InputStream): SpinnersFeatureConfig {
        return SpinnersFeatureConfig.parseFrom(input)
    }

    override suspend fun writeTo(t: SpinnersFeatureConfig, output: OutputStream) {
        t.writeTo(output)
    }
}
