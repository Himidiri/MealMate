package com.example.mealmate

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// Define the database and its entities
@Database(entities = [Meals::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class MealDatabase : RoomDatabase() {

    // Declare an abstract function to retrieve the DAO (Data Access Object)
    abstract fun mealDao(): MealsDao

    companion object {
        // Define a volatile variable to ensure visibility across threads
        @Volatile
        private var INSTANCE: MealDatabase? = null

        // Create a method to get the database instance
        fun getDatabase(context: Context): MealDatabase {

            // Check if an instance of the database already exists
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            // Synchronize the code to ensure only one thread creates the database instance
            synchronized(this) {
                // Build the database using Room.databaseBuilder and assign it to the instance
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "meal_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
