package fr.lotfirais.eatitup.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase

abstract class DAOBase(context: Context) {

    private var mDb: SQLiteDatabase? = null

    private val mHandler by lazy {
        SQLiteHelper.getInstance(context, DATABASE_NAME, null, DATABASE_VERSION)
    }

    private fun open() {
        mDb = mHandler?.writableDatabase
    }

    val db: SQLiteDatabase?
        get() {
            if (mDb == null) {
                open()
            }
            return mDb
        }

    companion object {
        private const val DATABASE_VERSION = 4
        private const val DATABASE_NAME = "meals.db"
    }
}