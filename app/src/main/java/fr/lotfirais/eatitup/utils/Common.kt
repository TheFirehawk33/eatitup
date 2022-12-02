package fr.lotfirais.eatitup.utils

import android.content.Context
import android.widget.Toast
import fr.lotfirais.eatitup.data.models.IngredientsHolder
import fr.lotfirais.eatitup.data.models.Meal
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


object Common {

    fun onFailure(context: Context, t: Throwable) {
        Toast.makeText(context,t.message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Fonctions nécessaires, l'API n'est pas qualitative sur ce point
     */
    fun ingredientsToList(meal: Meal) : ArrayList<IngredientsHolder> {
        val ingredientsList = ArrayList<IngredientsHolder>()

        if (!meal.strIngredient1.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient1, meal.strMeasure1))
        if (!meal.strIngredient2.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient2, meal.strMeasure2))
        if (!meal.strIngredient3.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient3, meal.strMeasure3))
        if (!meal.strIngredient4.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient4, meal.strMeasure4))
        if (!meal.strIngredient5.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient5, meal.strMeasure5))
        if (!meal.strIngredient6.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient6, meal.strMeasure6))
        if (!meal.strIngredient7.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient7, meal.strMeasure7))
        if (!meal.strIngredient8.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient8, meal.strMeasure8))
        if (!meal.strIngredient9.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient9, meal.strMeasure9))
        if (!meal.strIngredient10.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient10, meal.strMeasure10))
        if (!meal.strIngredient11.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient11, meal.strMeasure11))
        if (!meal.strIngredient12.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient12, meal.strMeasure12))
        if (!meal.strIngredient13.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient13, meal.strMeasure13))
        if (!meal.strIngredient14.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient14, meal.strMeasure14))
        if (!meal.strIngredient15.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient15, meal.strMeasure15))
        if (!meal.strIngredient16.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient16, meal.strMeasure16))
        if (!meal.strIngredient17.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient17, meal.strMeasure17))
        if (!meal.strIngredient18.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient18, meal.strMeasure18))
        if (!meal.strIngredient19.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient19, meal.strMeasure19))
        if (!meal.strIngredient20.isNullOrEmpty()) ingredientsList.add(IngredientsHolder(meal.strIngredient20, meal.strMeasure20))

        return ingredientsList
    }
}