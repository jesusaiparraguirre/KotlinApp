package com.example.myapplication.model.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LoggedDAO{
    @Query("SELECT * FROM logged ORDER BY user_id DESC LIMIT 1")
    fun getLogin() : LoggedEntity

    @Insert
    fun postLogin(logged : LoggedEntity)

    @Delete()
    fun deleteLogin(logged: LoggedEntity)
}