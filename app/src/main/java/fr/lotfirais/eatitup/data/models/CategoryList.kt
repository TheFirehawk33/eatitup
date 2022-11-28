package fr.lotfirais.eatitup.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CategoryList (
    val categories: List<Category>? = null
) : Parcelable