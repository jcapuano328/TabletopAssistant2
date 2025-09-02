package com.ica.tabletopassistant.features.odds.data

import androidx.datastore.core.Serializer
import com.ica.tabletopassistant.data.odds.OddsFeatureConfig
import java.io.InputStream
import java.io.OutputStream

object OddsSerializer : Serializer<OddsFeatureConfig> {
    //override val defaultValue: OddsFeatureConfig = OddsFeatureConfig.getDefaultInstance()
    override val defaultValue: OddsFeatureConfig = OddsFeatureConfig.newBuilder()
        .setIsEnabled(false) // custom default
        .setIsRounded(false)
        .setRoundingMode(1) // std
        .setAttack("1")
        .setDefend("1")
        .build()

    override suspend fun readFrom(input: InputStream): OddsFeatureConfig {
        return OddsFeatureConfig.parseFrom(input)
    }

    override suspend fun writeTo(t: OddsFeatureConfig, output: OutputStream) {
        t.writeTo(output)
    }
}
