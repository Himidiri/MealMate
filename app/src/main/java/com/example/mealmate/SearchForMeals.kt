package com.example.mealmate

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
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
    private lateinit var mealsContainer: LinearLayout

    var search: Button? = null
    private val stb = StringBuilder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_for_meals)

        db = MealDatabase.getDatabase(this)

        anyString = findViewById(R.id.anyString)
        search = findViewById(R.id.search)
        mealsContainer = findViewById(R.id.mealsContainer)

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
                        val ingredients =
                            meal.ingredients?.joinToString(separator = ",")?.toLowerCase()
                        if (mealName?.contains(query) == true || ingredients?.contains(query) == true) {
                            meals.add(meal)
                        }
                    }
                }

                if (meals.isEmpty()) {
                    mealsContainer.removeAllViews() // Clear previous search results
                    val noResultTextView = TextView(this@SearchForMeals)
                    noResultTextView.text = "No meals or ingredients found"
                    mealsContainer.addView(noResultTextView)
                } else {
                    mealsContainer.removeAllViews() // Clear previous search results
                    meals.forEach { meal ->
                        // Inflate meals_item.xml for each search result
                        val mealItemView = layoutInflater.inflate(R.layout.meal_item, null)
                        // Find views in meals_item.xml
                        val mealImage: ImageView = mealItemView.findViewById(R.id.mealImage)
                        val allMealsDetails: TextView = mealItemView.findViewById(R.id.allMealsDetails)

                        // Load thumbnail image using Glide
                        meal.mealThumb?.let { thumbnailUrl ->
                            Glide.with(this@SearchForMeals)
                                .load(thumbnailUrl)
                                .into(mealImage)
                        }

                        // Build the details string
                        stb.clear()
                        stb.append("\"Meal\": \"${meal.meal}\",\n")
                        stb.append("\"Drink Alternate\": ${meal.drinkAlternate},\n")
                        stb.append("\"Category\": \"${meal.category}\",\n")
                        stb.append("\"Area\": \"${meal.area}\",\n")
                        stb.append("\"Instructions\": \"${meal.instructions}\",\n")
                        stb.append("\"Tags\": ${meal.tags},\n")
                        stb.append("\"Youtube\": \"${meal.youtube}\",\n")

                        meal.ingredients?.forEachIndexed { index, ingredient ->
                            stb.append("\"Ingredient${index + 1}\": \"$ingredient\",\n")
                        }

                        meal.measures?.forEachIndexed { index, measure ->
                            stb.append("\"Measure${index + 1}\": \"$measure\",\n")
                        }
                        stb.append("\n\n")
                        // Set the details string to the TextView
                        allMealsDetails.text = stb.toString().trim()

                        // Add the meals_item view to the mealsContainer LinearLayout
                        mealsContainer.addView(mealItemView)
                    }
                }
            }
        }
    }
}