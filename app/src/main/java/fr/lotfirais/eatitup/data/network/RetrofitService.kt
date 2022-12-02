package fr.lotfirais.eatitup.data.network

import fr.lotfirais.eatitup.data.models.CategoryList
import fr.lotfirais.eatitup.data.models.Ingredients
import fr.lotfirais.eatitup.data.models.Meals
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("latest.php/")
    fun getLatestMeals(): Single<Meals>

    @GET("categories.php")
    fun getMealCategories(): Single<CategoryList>

    @GET("random.php")
    fun getRandomMeal(): Single<Meals>

    @GET("lookup.php?i=")
    fun getMealById(@Query("i")id:String): Single<Meals>

    @GET("search.php?s=")
    fun searchMealByName(@Query("s")search:String): Single<Meals>

    @GET("filter.php?i=")
    fun searchMealByIngredient(@Query("i")search:String): Single<Meals>

    @GET("list.php?i=list")
    fun getAllIngredients():Single<Ingredients>

    @GET("search.php?f=")
    fun searchMealByFirstLetter(@Query("f")firstLetter:String): Single<Meals>
}