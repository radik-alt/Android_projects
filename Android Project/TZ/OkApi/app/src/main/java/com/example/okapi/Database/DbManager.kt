package com.example.okapi.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.sql.Date

class DbManager (val context: Context) {

    val dbHelper = DbHelper(context)
    var sqlDB: SQLiteDatabase? = null

    fun open () {
        sqlDB = dbHelper.writableDatabase
    }

    fun insert (first_name: String, last_name: String,
                birthday: String, position: String) {

        var values = ContentValues().apply {
            put(DbScheme.F_NAME, first_name)
            put(DbScheme.L_NAME, last_name)
            put(DbScheme.BIRTHDAY, birthday)
            put(DbScheme.POSITION, position)
        }

        sqlDB?.insert(DbScheme.TABLE_NAME, null, values)
    }

    fun readData () : ArrayList<String> {

        val dataList = ArrayList<String>()

        val cursor = sqlDB?.query(
            DbScheme.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor?.moveToNext()!!) {
            var index: Int = cursor.getColumnIndex(DbScheme.F_NAME)
            var dataText = cursor.getString(index)
            dataList.add(dataText.toString())
        }

        cursor.close()
        return dataList
    }

    fun close () {
        sqlDB?.close()
    }

}