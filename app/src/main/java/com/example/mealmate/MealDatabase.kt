package com.example.mealmate

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// Define the schema of the database using Room annotations
@Database(entities = [Meals::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealsDao
}
