package com.example.mealmate

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meals(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val meal: String?,
    val drinkAlternate: String?,
    val category: String?,
    val area: String?,
    val instructions: String?,
    val mealThumb: String?,
    val tags: String?,
    val youtube: String?,
    val ingredients: List<String>?,
    val measures: List<String>?,
    val Source: String?,
    val ImageSource: String?,
    val CreativeCommonsConfirmed : String?,
    val dateModified: String?
) {
    // Constructor
    constructor(
        meal: String?,
        drinkAlternate: String?,
        category: String?,
        area: String?,
        instructions: String?,
        mealThumb: String?,
        tags: String?,
        youtube: String?,
        ingredients: List<String>?,
        measures: List<String>?,
        Source: String?,
        ImageSource: String?,
        CreativeCommonsConfirmed : String?,
        dateModified: String?
    ) : this(
        0,
        meal,
        drinkAlternate,
        category,
        area,
        instructions,
        mealThumb,
        tags,
        youtube,
        ingredients,
        measures,
        Source,
        ImageSource,
        CreativeCommonsConfirmed,
        dateModified
    )
}


