package com.example.mealmate

import androidx.room.TypeConverter

class StringListConverter {

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.split(",")
    }

    @TypeConverter
    fun toString(value: List<String>?): String? {
        return value?.joinToString(",")
    }

}
