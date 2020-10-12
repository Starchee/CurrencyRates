package com.starchee.currencyrates.model.local

import androidx.room.Embedded
import androidx.room.Relation

data class RateLocal(

    @Embedded var savedDateLocal: SavedDateLocal,

    @Relation(parentColumn = "id", entityColumn = "date_id", entity = CurrencyLocal::class)
    var currencyLocalList: List<CurrencyLocal>
) {
}