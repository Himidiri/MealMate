package com.example.mealmate

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Define the entity for the Meals table in the database
@Entity(tableName = "Meals")
data class Meals(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Primary key for the Meals table, auto-generated
    @ColumnInfo(name = "Meal") val meal: String?, // Name of the meal
    @ColumnInfo(name = "DrinkAlternate") val drinkAlternate: String?, // Alternate drink option
    @ColumnInfo(name = "Category") val category: String?, // Category of the meal
    @ColumnInfo(name = "Area") val area: String?, // Area or region associated with the meal
    @ColumnInfo(name = "Instructions") val instructions: String?, // Instructions to prepare the meal
    @ColumnInfo(name = "MealThumb") val mealThumb: String?, // Thumbnail image URL for the meal
    @ColumnInfo(name = "Tags") val tags: String?, // Tags or keywords associated with the meal
    @ColumnInfo(name = "Youtube") val youtube: String?, // YouTube video URL for the meal
    @ColumnInfo(name = "Ingredients") val ingredients: List<String>?, // List of ingredients required for the meal
    @ColumnInfo(name = "Measures") val measures: List<String>?, // List of measurement units for the ingredients
    @ColumnInfo(name = "Source") val source: String?, // Source of the meal information
    @ColumnInfo(name = "ImageSource") val imageSource: String?, // Image source URL for the meal
    @ColumnInfo(name = "CreativeCommonsConfirmed") val creativeCommonsConfirmed: String?, // Confirmation of Creative Commons license
    @ColumnInfo(name = "DateModified") val dateModified: String? // Date of the last modification to the meal
)
