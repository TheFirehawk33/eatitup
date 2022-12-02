package fr.lotfirais.eatitup.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context, dbName: String, factory : CursorFactory?, dbVersion: Int) : SQLiteOpenHelper(context, dbName, factory, dbVersion) {

    companion object {
        private var mInstance: SQLiteOpenHelper? = null
        fun getInstance(context: Context, name: String, factory: CursorFactory?, version: Int): SQLiteOpenHelper? {
            if (mInstance == null) {
                mInstance = SQLiteHelper(context, name, factory, version)
            }
            return mInstance
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblMeals = ("CREATE TABLE " + TBL_MEALS + "("
                + MEAL_ID + " TEXT PRIMARY KEY, "
                + MEAL_NAME + " TEXT, "
                + MEAL_CATEGORY + " TEXT, "
                + MEAL_THUMB + " TEXT, "
                + MEAL_INSTRUCTIONS + " TEXT "
                + ")")
        db?.execSQL(createTblMeals);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_MEALS")
        onCreate(db)
    }


}