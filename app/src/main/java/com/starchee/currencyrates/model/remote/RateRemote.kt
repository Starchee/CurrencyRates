package com.starchee.currencyrates.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.starchee.currencyrates.model.local.RateLocal
import com.starchee.currencyrates.model.local.SavedDateLocal

data class RateRemote(

    @SerializedName("Date")
    @Expose
    val date: String,

    @SerializedName("Valute")
    @Expose
    val valute: HashMap<String, CurrencyRemote>
) {

    fun mappingToRateLocal(saveDateId: Long): RateLocal {
        val savedDate = SavedDateLocal(id = saveDateId, date = date)
        val currencyRemote = ArrayList(valute.values)
        val currencyLocal = currencyRemote.map {it.mappingToCurrencyLocal(saveDateId) }
        return  RateLocal(savedDate, currencyLocalList = currencyLocal)
    }
}