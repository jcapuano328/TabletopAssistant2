package com.ica.tabletopassistant

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

import com.ica.tabletopassistant.data.dice.DiceFeatureConfig
import com.ica.tabletopassistant.features.dice.data.DiceRepository
import com.ica.tabletopassistant.features.dice.data.DiceSerializer

import com.ica.tabletopassistant.data.odds.OddsFeatureConfig
import com.ica.tabletopassistant.features.odds.data.OddsRepository
import com.ica.tabletopassistant.features.odds.data.OddsSerializer

import com.ica.tabletopassistant.data.spinners.SpinnersFeatureConfig
import com.ica.tabletopassistant.features.spinners.data.SpinnersRepository
import com.ica.tabletopassistant.features.spinners.data.SpinnersSerializer

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDiceDataStore(@ApplicationContext context: Context): DataStore<DiceFeatureConfig> {
        return DataStoreFactory.create(
            serializer = DiceSerializer,
            produceFile = {
                File(context.filesDir, "dice_feature_config.pb")
            }
        )
    }

    @Provides
    @Singleton
    fun provideDiceRepository(
        diceDataStore: DataStore<DiceFeatureConfig>
    ): DiceRepository = DiceRepository(diceDataStore)

    @Provides
    @Singleton
    fun provideOddsDataStore(@ApplicationContext context: Context): DataStore<OddsFeatureConfig> {
        return DataStoreFactory.create(
            serializer = OddsSerializer,
            produceFile = { File(context.filesDir, "odds_feature_config.pb") }
        )
    }

    @Provides
    @Singleton
    fun provideOddsRepository(
        dataStore: DataStore<OddsFeatureConfig>
    ): OddsRepository = OddsRepository(dataStore)

    @Provides
    @Singleton
    fun provideSpinnersDataStore(@ApplicationContext context: Context): DataStore<SpinnersFeatureConfig> {
        return DataStoreFactory.create(
            serializer = SpinnersSerializer,
            produceFile = { File(context.filesDir, "spinners_feature_config.pb") }
        )
    }

    @Provides
    @Singleton
    fun provideSpinnersRepository(
        dataStore: DataStore<SpinnersFeatureConfig>
    ): SpinnersRepository = SpinnersRepository(dataStore)

}
