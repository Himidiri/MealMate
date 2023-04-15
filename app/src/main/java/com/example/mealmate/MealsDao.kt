package com.example.mealmate

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MealsDao {
    @Insert
    fun insert(meal: Meals)

    @Update
    suspend fun update(meal: Meals)

    @Query("DELETE FROM meals WHERE id = :id")
    fun delete(id: Int): Int


    @Query("SELECT * FROM meals")
    suspend fun getAll(): List<Meals>
}
