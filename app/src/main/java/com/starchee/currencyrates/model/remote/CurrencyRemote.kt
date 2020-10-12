package com.starchee.currencyrates.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.starchee.currencyrates.model.local.CurrencyLocal
import java.text.DecimalFormat

data class CurrencyRemote (
    @SerializedName("NumCode")
    @Expose
    val id: Long,

    @SerializedName("CharCode")
    @Expose
    val charCode: String,

    @SerializedName("Nominal")
    @Expose
    val nominal: Int,

    @SerializedName("Name")
    @Expose
    val name: String,

    @SerializedName("Value")
    @Expose
    val value: Float,

    @SerializedName("Previous")
    @Expose
    val previous: Float
) {
    fun mappingToCurrencyLocal(saveDateId: Long): CurrencyLocal {
        val formatter = DecimalFormat("##.0000")
        val difference = formatter.format(value - previous).toString().toFloat()

        return  CurrencyLocal(
            id = id,
            charCode = charCode,
            nominal = nominal,
            name = name,
            value = value,
            difference = difference,
            dateId = saveDateId
        )
    }
}