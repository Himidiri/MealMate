package com.example.mealmate

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Meals")
data class Meals(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "Meal") val meal: String?,
    @ColumnInfo(name = "DrinkAlternate") val drinkAlternate: String?,
    @ColumnInfo(name = "Category") val category: String?,
    @ColumnInfo(name = "Area") val area: String?,
    @ColumnInfo(name = "Instructions") val instructions: String?,
    @ColumnInfo(name = "MealThumb") val mealThumb: String?,
    @ColumnInfo(name = "Tags") val tags: String?,
    @ColumnInfo(name = "Youtube") val youtube: String?,
    @ColumnInfo(name = "Ingredients") val ingredients: List<String>?,
    @ColumnInfo(name = "Measures") val measures: List<String>?,
    @ColumnInfo(name = "Source") val source: String?,
    @ColumnInfo(name = "ImageSource") val imageSource: String?,
    @ColumnInfo(name = "CreativeCommonsConfirmed") val creativeCommonsConfirmed : String?,
    @ColumnInfo(name = "DateModified") val dateModified: String?)



