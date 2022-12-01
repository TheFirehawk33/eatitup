package fr.lotfirais.eatitup.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredients(
    var meals: List<Ingredient>? = null
) : Parcelable
