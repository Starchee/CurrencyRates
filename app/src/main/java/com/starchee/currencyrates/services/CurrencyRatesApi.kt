package com.starchee.currencyrates.services

import com.starchee.currencyrates.model.remote.RateRemote
import io.reactivex.Single
import retrofit2.http.GET

interface CurrencyRatesApi {

    @GET("daily_json.js")
    fun getAllRates() : Single<RateRemote>
}