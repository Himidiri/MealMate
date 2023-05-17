package com.example.mealmate

import androidx.room.*

// Define the data access object (DAO) for accessing the Meals table in the database
@Dao
interface MealsDao {
    // Insert meals into the Meals table, ignoring conflicts
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMeals(vararg meals: Meals)

    // Insert all meals into the Meals table
    @Insert
    suspend fun insertAll(vararg meals: Meals)

    // Update a meal in the Meals table
    @Update
    suspend fun update(meal: Meals)

    // Delete a meal from the Meals table based on its ID
    @Query("DELETE FROM meals WHERE id = :id")
    fun delete(id: Int): Int

    // Retrieve all meals from the Meals table
    @Query("SELECT * FROM meals")
    suspend fun getAll(): List<Meals>
}
