package fr.lotfirais.eatitup.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meals (
    var meals: List<Meal>? = null
) : Parcelable