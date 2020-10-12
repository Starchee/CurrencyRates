package com.starchee.currencyrates.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.starchee.currencyrates.model.local.CurrencyLocal
import com.starchee.currencyrates.model.local.SavedDateLocal

@Database(
    entities = [CurrencyLocal::class, SavedDateLocal::class],
    version = 1,
    exportSchema = false)
abstract class RatesDatabase : RoomDatabase(){
    abstract fun ratesDao(): RatesDao
}