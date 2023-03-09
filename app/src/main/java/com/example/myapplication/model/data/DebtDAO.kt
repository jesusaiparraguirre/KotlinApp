package com.example.myapplication.model.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DebtDAO{
    @Query("SELECT * FROM debt WHERE")
    fun getDebts() : List<DebtEntity>

    @Insert
    fun postLogin(logged : LoggedEntity)

    @Delete()
    fun deleteLogin(logged: LoggedEntity)
}