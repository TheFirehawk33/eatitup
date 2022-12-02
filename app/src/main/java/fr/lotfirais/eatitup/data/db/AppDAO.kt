package fr.lotfirais.eatitup.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import fr.lotfirais.eatitup.data.models.Meal
import io.reactivex.rxjava3.core.Single

class AppDAO(context : Context) : DAOBase(context) {

    fun insertFavoriteMeal(nt: Meal) : Single<Long> {
        return Single.create{
            insert(TBL_MEALS, contentValues = ContentValues().apply {
                put(MEAL_ID, nt.idMeal)
                put(MEAL_NAME, nt.strMeal)
                put(MEAL_CATEGORY, nt.strCategory)
                put(MEAL_INSTRUCTIONS, nt.strInstructions)
                put(MEAL_THUMB, nt.strMealThumb)
            })?.let { id ->
                it.onSuccess(id)
            }
        }
    }

    fun getAllFavoriteMeals() : Single<ArrayList<Meal>> {
        return Single.create{ emitter ->
            val ntList: ArrayList<Meal> = ArrayList()

            query(TBL_MEALS)?.let {
                while (it.moveToNext()) {
                    ntList.add(Meal(
                        it.getString(it.getColumnIndexOrThrow(MEAL_ID)),
                        it.getString(it.getColumnIndexOrThrow(MEAL_NAME)),
                        it.getString(it.getColumnIndexOrThrow(MEAL_CATEGORY)),
                        it.getString(it.getColumnIndexOrThrow(MEAL_THUMB)),
                        it.getString(it.getColumnIndexOrThrow(MEAL_INSTRUCTIONS))
                    ))
                }
                it.close()
            }

            emitter.onSuccess(ntList)
        }
    }

    fun deleteFavoriteMealById(id: Int) {
        delete(TBL_MEALS, whereClause = "$MEAL_ID=$id")
    }

    private fun insert(tableName: String, contentValues: ContentValues? = null): Long? {
        return db?.insert(tableName, null, contentValues)
    }

    private fun update(tableName: String, contentValues: ContentValues, whereClause: String? = null, whereArgs: Array<String>? = null) {
        db?.update(tableName, contentValues, whereClause, whereArgs)
    }

    private fun delete(tableName: String, whereClause: String? = null, whereArgs: Array<String>? = null) {
        db?.delete(tableName, whereClause, whereArgs)
    }

    private fun query(tableName: String, columns: Array<String>? = null, selection: String? = null, selectionArgs: Array<String>? = null, groupBy: String? = null, having: String? = null, orderBy: String? = null, limit: String? = null): Cursor? {
        return db?.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit)
    }
}