package fr.lotfirais.eatitup.data.network

import fr.lotfirais.eatitup.data.models.CategoryList
import fr.lotfirais.eatitup.data.models.Meals
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("latest.php/")
    fun getLatestMeals(): Observable<Meals>

    @GET("categories.php")
    fun getMealCategories(): Observable<CategoryList>

    @GET("random.php")
    fun getRandomMeal(): Observable<Meals>

    @GET("https://www.themealdb.com/api/json/v1/1/lookup.php?i=")
    fun getMealById(@Query("i")id:String): Observable<Meals>
}