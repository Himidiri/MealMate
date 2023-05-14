package com.example.mealmate

import androidx.room.*

@Dao
interface MealsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMeals(vararg user: Meals)

    @Insert
    suspend fun insertAll(vararg users: Meals)
    @Update
    suspend fun update(meal: Meals)

    @Query("DELETE FROM meals WHERE id = :id")
    fun delete(id: Int): Int

    @Query("SELECT * FROM meals")
    suspend fun getAll(): List<Meals>

    @Query("SELECT * FROM Meals WHERE meal LIKE '%' || :searchQuery || '%' OR ingredients LIKE '%' || :searchQuery || '%'")
    suspend fun searchMeals(searchQuery: String): List<Meals>

}
