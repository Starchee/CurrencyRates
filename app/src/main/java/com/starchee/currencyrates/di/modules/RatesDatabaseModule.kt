package com.starchee.currencyrates.di.modules

import android.app.Application
import androidx.room.Room
import com.starchee.currencyrates.database.RatesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RatesDatabaseModule {

    companion object {
        private const val DATABASE_NAME = "rates_database"
    }

    @Singleton
    @Provides
    fun provideRatesDatabase(application: Application): RatesDatabase {
        return Room.databaseBuilder(application, RatesDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}