package com.example.myapplication.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logged")
data class LoggedEntity(
    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = false)
    val userId: Int = 0,

    @ColumnInfo(name = "user_name")
    val userName: String
)