package com.starchee.currencyrates.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyLocal (

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "char_code")
    val charCode: String,

    @ColumnInfo(name = "nominal")
    val nominal: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "value")
    val value: Float,

    @ColumnInfo(name = "difference")
    var difference: Float,

    @ColumnInfo(name = "date_id" , index = true)
    var dateId: Long
)