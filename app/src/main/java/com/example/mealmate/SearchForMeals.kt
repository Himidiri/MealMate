package com.example.mealmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchForMeals : AppCompatActivity() {

    private lateinit var db: MealDatabase
    private val meals = mutableListOf<Meals>()
    private lateinit var anyString: EditText
    private lateinit var allMealsDetails: TextView

    var search: Button? = null
    private val stb = StringBuilder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_for_meals)

        db = MealDatabase.getDatabase(this)

        anyString = findViewById(R.id.anyString)
        search = findViewById(R.id.search)
        allMealsDetails = findViewById(R.id.allMealsDetails)

        searchButton()
    }

    private fun searchButton() {
        search?.setOnClickListener {
            val query = anyString.text.toString().trim().toLowerCase()
            if (query.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter a meal name or an ingredient",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            runBlocking {
                withContext(Dispatchers.IO) {
                    meals.clear()
                    val allMeals = db.mealDao().getAll()
                    for (meal in allMeals) {
                        val mealName = meal.meal?.toLowerCase()
                        val ingredients = meal.ingredients?.joinToString(separator = ",")?.toLowerCase()
                        if (mealName?.contains(query) == true || ingredients?.contains(query) == true) {
                            meals.add(meal)
                        }
                    }
                }

                if (meals.isEmpty()) {
                    allMealsDetails.text = "No meals or ingredients found"
                } else {
                    stb.clear()
                    meals.forEach { meal ->

                        stb.append("\"Meal\" : \"${meal.meal}\" , \n")
                        stb.append("\"Drink Alternate\" : \" ${meal.drinkAlternate}\" ,\n")
                        stb.append("\"Category\" : \"${meal.category}\" ,\n")
                        stb.append("\"Area\" : \"${meal.area}\" ,\n")
                        stb.append("\"Instructions\" : \"${meal.instructions}\" ,\n")
                        stb.append("\"MealThumb\" : \"${meal.mealThumb}\" ,\n")
                        stb.append("\"Tags\" : \"${meal.tags}\" ,\n")
                        stb.append("\"YouTube\" : \"${meal.youtube}\" ,\n")

                        meal.ingredients?.forEachIndexed { index, ingredient ->
                            stb.append("\"Ingredient${index + 1}\" : \"$ingredient\" , \n")
                        }
                        meal.measures?.forEachIndexed { index, measure ->
                            stb.append("\"Measure${index + 1}\" : \"$measure\" , \n")
                        }
                        stb.append("\n\n")
                    }
                    allMealsDetails.text = stb.toString().trim()
                }
            }
        }
    }
}