package com.starchee.currencyrates.repositories

import com.starchee.currencyrates.database.RatesDatabase
import com.starchee.currencyrates.model.local.CurrencyLocal
import com.starchee.currencyrates.model.local.RateLocal
import com.starchee.currencyrates.services.RetrofitService
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RatesRepository @Inject constructor(
    private val ratesDatabase: RatesDatabase,
    private val retrofitService: RetrofitService
) {

    companion object {
        private const val SAVED_DATE_ID = 0L
    }

    fun loadRates(): Single<RateLocal> = ratesDatabase.ratesDao().getAllRate(savedDateId = SAVED_DATE_ID)


    fun updateRates(): Single<List<CurrencyLocal>> {

        return retrofitService.loadRates()
            .delay(3, TimeUnit.SECONDS)
            .flatMap { response -> Single.just(response.mappingToRateLocal(SAVED_DATE_ID))}
            .doOnSuccess { ratesDatabase.ratesDao().insertCurrency(rateLocal = it) }
            .flatMap { rate -> Single.just(rate.currencyLocalList) }
    }
}