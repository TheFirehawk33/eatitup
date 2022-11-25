package fr.lotfirais.eatitup.models

data class Recipe(
    val id: Number,
    val name: String,
    val drinkAlternate: String,
    val category: String,
    val area: String,
    val instruction: String,
    val picturePath: String,
    val tags: String,
    val youtubeUrl: String,
    val ingredients: List<String>,
    val measurements: List<String>,
)
