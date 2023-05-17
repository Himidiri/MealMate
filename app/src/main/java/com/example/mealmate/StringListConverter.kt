package com.example.mealmate

import androidx.room.TypeConverter

class StringListConverter {

    // This class provides type conversion methods for converting between a List<String> and a String.

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        // This method converts a comma-separated String to a List<String>.
        // The input value is the String to be converted.
        // It returns the List<String> representation of the input String.

        return value?.split(",")
    }

    @TypeConverter
    fun toString(value: List<String>?): String? {
        // This method converts a List<String> to a comma-separated String.
        // The input value is the List<String> to be converted.
        // It returns the comma-separated String representation of the input List<String>.

        return value?.joinToString(",")
    }

}
