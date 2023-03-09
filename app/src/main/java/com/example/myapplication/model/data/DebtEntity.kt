package com.example.myapplication.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "debt")
data class DebtEntity(
    @ColumnInfo(name = "debt_id")
    @PrimaryKey(autoGenerate = true)
    val debtId: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: Int = 0,

    @ColumnInfo(name = "debt_title")
    val title: String,

    @ColumnInfo(name = "debt_amount")
    val amount: String
)