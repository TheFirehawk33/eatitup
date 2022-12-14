package fr.lotfirais.eatitup.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val idCategory: String? = null,
    val strCategory: String? = null,
    val strCategoryThumb: String? = null,
    val strCategoryDescription: String? = null
) : Parcelable