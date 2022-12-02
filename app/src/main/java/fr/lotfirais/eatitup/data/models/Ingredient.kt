package fr.lotfirais.eatitup.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val idIngredient: String? = null,
    val strIngredient: String? = null,
    val strDescription: String? = null,
    val strType: String? = null
) : Parcelable
