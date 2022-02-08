package com.example.okapi.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DbScheme.DB_NAME, null, DbScheme.VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DbScheme.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(DbScheme.DELETE_TABLE)
        onCreate(db)
    }

}