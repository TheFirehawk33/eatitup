package fr.lotfirais.eatitup.utils

import android.content.Context
import android.widget.Toast
import fr.lotfirais.eatitup.data.models.IngredientsHolder
import fr.lotfirais.eatitup.data.models.Meal


object Common {
    fun onFailure(context: Context, t: Throwable) {
        Toast.makeText(context,t.message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Fonctions nécessaires, l'API n'est pas qualitative sur ce point
     */
    fun ingredientsToList(meal: Meal) : ArrayList<IngredientsHolder> {
        val ingredientsList = ArrayList<IngredientsHolder>()

        if (meal.strIngredient1 != "" && meal.strIngredient1 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient1, meal.strMeasure1))
        if (meal.strIngredient2 != "" && meal.strIngredient2 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient2, meal.strMeasure2))
        if (meal.strIngredient3 != "" && meal.strIngredient3 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient3, meal.strMeasure3))
        if (meal.strIngredient4 != "" && meal.strIngredient4 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient4, meal.strMeasure4))
        if (meal.strIngredient5 != "" && meal.strIngredient5 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient5, meal.strMeasure5))
        if (meal.strIngredient6 != "" && meal.strIngredient6 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient6, meal.strMeasure6))
        if (meal.strIngredient7 != "" && meal.strIngredient7 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient7, meal.strMeasure7))
        if (meal.strIngredient8 != "" && meal.strIngredient8 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient8, meal.strMeasure8))
        if (meal.strIngredient9 != "" && meal.strIngredient9 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient9, meal.strMeasure9))
        if (meal.strIngredient10 != "" && meal.strIngredient10 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient10, meal.strMeasure10))
        if (meal.strIngredient11 != "" && meal.strIngredient11 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient11, meal.strMeasure11))
        if (meal.strIngredient12 != "" && meal.strIngredient12 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient12, meal.strMeasure12))
        if (meal.strIngredient13 != "" && meal.strIngredient13 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient13, meal.strMeasure13))
        if (meal.strIngredient14 != "" && meal.strIngredient14 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient14, meal.strMeasure14))
        if (meal.strIngredient15 != "" && meal.strIngredient15 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient15, meal.strMeasure15))
        if (meal.strIngredient16 != "" && meal.strIngredient16 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient16, meal.strMeasure16))
        if (meal.strIngredient17 != "" && meal.strIngredient17 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient17, meal.strMeasure17))
        if (meal.strIngredient18 != "" && meal.strIngredient18 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient18, meal.strMeasure18))
        if (meal.strIngredient19 != "" && meal.strIngredient19 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient19, meal.strMeasure19))
        if (meal.strIngredient20 != "" && meal.strIngredient20 != null) ingredientsList.add(IngredientsHolder(meal.strIngredient20, meal.strMeasure20))

        return ingredientsList
    }
}