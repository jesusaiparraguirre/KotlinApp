package com.example.myapplication.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, LoggedEntity::class], version= 2)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun userDAO() : UserDAO
    abstract fun loginDAO() : LoggedDAO

    companion object{
        private var INSTANCE: ApplicationDatabase? = null

        fun getAppDatabase(context: Context): ApplicationDatabase? {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "WalleyDatabase"
                ).allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}