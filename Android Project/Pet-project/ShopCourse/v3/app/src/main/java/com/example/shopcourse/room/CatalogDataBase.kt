package com.example.shopcourse.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Catalog::class], version = 1, exportSchema = false)
abstract class CatalogDataBase : RoomDatabase() {

    abstract fun useDao() : UseDao

    companion object {
        @Volatile
        private var INSTANCE : CatalogDataBase? = null

        fun getDB (context: Context) : CatalogDataBase {
            val tempInstatce = INSTANCE
            if (tempInstatce != null) {
                return tempInstatce
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatalogDataBase::class.java,
                    "catalogDB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}