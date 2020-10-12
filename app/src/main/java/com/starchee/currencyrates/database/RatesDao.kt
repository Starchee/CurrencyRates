package com.starchee.currencyrates.database

import androidx.room.*
import com.starchee.currencyrates.model.local.CurrencyLocal
import com.starchee.currencyrates.model.local.RateLocal
import com.starchee.currencyrates.model.local.SavedDateLocal
import io.reactivex.Single

@Dao
abstract class RatesDao {

    @Transaction
    open fun insertCurrency(rateLocal: RateLocal) {
        val dateId = insertDate(savedDateLocal = rateLocal.savedDateLocal)
        rateLocal.currencyLocalList.forEach { it.dateId = dateId}
        insertCurrency(currencyLocalList = rateLocal.currencyLocalList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrency(currencyLocalList : List<CurrencyLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDate(savedDateLocal: SavedDateLocal): Long

    @Transaction
    @Query("SELECT * FROM saved_date WHERE id LIKE :savedDateId")
    abstract fun getAllRate(savedDateId: Long): Single<RateLocal>
}