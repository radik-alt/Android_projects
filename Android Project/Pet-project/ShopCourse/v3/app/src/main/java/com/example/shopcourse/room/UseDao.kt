package com.example.shopcourse.room

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface UseDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct (catalog: Catalog)

    @Query ("SELECT * FROM catalogDB ORDER BY id ASC")
    fun readProduct () : LiveData<List<Catalog>>

}