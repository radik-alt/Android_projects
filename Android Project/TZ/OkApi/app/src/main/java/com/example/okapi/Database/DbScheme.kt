package com.example.okapi.Database

import android.provider.BaseColumns
import java.sql.Date

object DbScheme : BaseColumns {

    const val TABLE_NAME : String = "table_name"
    const val DB_NAME : String = "my_db.db"

    const val F_NAME: String = "F_NAME"
    const val L_NAME: String = "L_NAME"
    const val BIRTHDAY: String = "BIRTHDAY"
    const val POSITION : String = "POSITION"

    const val VERSION : Int = 1

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME " +
            "(${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "$F_NAME TEXT," +
            "$L_NAME TEXT," +
            "$BIRTHDAY TEXT," +
            "$POSITION TEXT)"

    const val DELETE_TABLE = "DROP_TABLE IF EXISTS $TABLE_NAME"
}