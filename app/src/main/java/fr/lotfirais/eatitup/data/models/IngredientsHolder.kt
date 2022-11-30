package fr.lotfirais.eatitup.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientsHolder(
    val strIngredient: String? = null,
    val strMeasure: String? = null,
) : Parcelable