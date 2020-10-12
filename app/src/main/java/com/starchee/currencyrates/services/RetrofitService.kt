package com.starchee.currencyrates.services

import com.starchee.currencyrates.model.remote.RateRemote
import io.reactivex.Single

import retrofit2.Retrofit

class RetrofitService constructor(
    private val retrofit: Retrofit){

    fun loadRates(): Single<RateRemote> {
        return retrofit.create(CurrencyRatesApi::class.java).getAllRates()
    }
}