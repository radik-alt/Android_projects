package com.example.okapi.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.fragmenttz.Models
import java.sql.Date
import java.util.ArrayList

class DbManager (val context: Context) {

    val dbHelper = DbHelper(context)
    var sqlDB: SQLiteDatabase? = null

    fun open () {
        sqlDB = dbHelper.writableDatabase
    }

    fun insert (first_name: String, last_name: String,
                birthday: String, age: Int, position: String) {

        var values = ContentValues().apply {
            put(DbScheme.F_NAME, first_name)
            put(DbScheme.L_NAME, last_name)
            put(DbScheme.BIRTHDAY, birthday)
            put(DbScheme.AGE, age)
            put(DbScheme.POSITION, position)
        }

        sqlDB?.insert(DbScheme.TABLE_NAME, null, values)
    }

    fun readData () : MutableSet<Models> {

        val dataList: MutableSet<Models> = mutableSetOf()

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
            var indexPos: Int = cursor.getColumnIndex(DbScheme.POSITION)
            var pos = cursor.getString(indexPos)

            var indexFname: Int = cursor.getColumnIndex(DbScheme.F_NAME)
            var f_name = cursor.getString(indexFname)

            var indexLname: Int = cursor.getColumnIndex(DbScheme.L_NAME)
            var l_name = cursor.getString(indexLname)

            var indexBirthday: Int = cursor.getColumnIndex(DbScheme.BIRTHDAY)
            var birth = cursor.getString(indexBirthday)

            var ageIndex : Int = cursor.getColumnIndex(DbScheme.AGE)
            var age = cursor.getString(ageIndex)

            dataList.add(Models(f_name.toString(), l_name.toString(), birth.toString(), age.toInt(), pos.toString()))
        }

        cursor.close()
        return dataList
    }

    fun getPosition () : MutableSet<String> {

        var arr: MutableSet<String> = mutableSetOf()

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
            var indexPos: Int = cursor.getColumnIndex(DbScheme.POSITION)
            var pos = cursor.getString(indexPos)
            arr.add(pos)
        }

        return arr
    }

    fun close () {
        sqlDB?.close()
    }

}