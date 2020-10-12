package com.starchee.currencyrates.model.local

import androidx.room.*


@Entity(tableName = "saved_date", indices = [Index(value = ["date"], unique = true)])
data class SavedDateLocal(

    @PrimaryKey(autoGenerate = false)
    val id: Long,

    @ColumnInfo(name = "date")
    val date: String
)