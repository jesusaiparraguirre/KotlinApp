package com.example.myapplication.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO{
    @Query("SELECT  * FROM users")
    fun getAll() : List<UserEntity>

    @Query("SELECT * FROM users WHERE user_name LIKE :username")
    fun findUserByUsername(username: String) : List<UserEntity>

    @Query("SELECT * FROM users ORDER BY user_id DESC LIMIT 1")
    fun getLastUser() : UserEntity

    @Insert
    fun saveUser(user : UserEntity)
}