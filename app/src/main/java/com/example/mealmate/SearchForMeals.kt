package com.example.mealmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

// Activity for searching meals
class SearchForMeals : AppCompatActivity() {

    private lateinit var db: MealDatabase
    private val meals = mutableListOf<Meals>()
    private lateinit var anyString: EditText
    private lateinit var mealsContainer: LinearLayout

    var search: Button? = null
    private var savedMealImages: MutableList<String?> = mutableListOf()
    private var savedAllMealsDetails: MutableList<String?> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_for_meals)

        // Initialize the MealDatabase
        db = MealDatabase.getDatabase(this)

        // Initialize views
        anyString = findViewById(R.id.anyString)
        search = findViewById(R.id.search)
        mealsContainer = findViewById(R.id.mealsContainer)

        // Set click listener for search button
        searchButton()

        // Retrieve saved instance state data
        savedInstanceState?.let {
            savedMealImages =
                it.getStringArrayList("mealImages")?.toMutableList() ?: mutableListOf()
            savedAllMealsDetails =
                it.getStringArrayList("allMealsDetails")?.toMutableList() ?: mutableListOf()
        }

        // Display saved meal images and details
        savedMealImages.forEachIndexed { index, imageUrl ->
            val mealItemView = layoutInflater.inflate(R.layout.meal_item, null)
            val mealImage: ImageView = mealItemView.findViewById(R.id.mealImage)
            // Load image using Glide library
            Glide.with(this@SearchForMeals)
                .load(imageUrl)
                .into(mealImage)
            mealsContainer.addView(mealItemView)

            savedAllMealsDetails.getOrNull(index)?.let { details ->
                val allMealsDetails: TextView = mealItemView.findViewById(R.id.allMealsDetails)
                allMealsDetails.text = details
            }
        }
    }

    // Save instance state data
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("mealImages", ArrayList(savedMealImages))
        outState.putStringArrayList("allMealsDetails", ArrayList(savedAllMealsDetails))
    }

    // Set click listener for the search button
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
                    // Retrieve all meals from the database
                    val allMeals = db.mealDao().getAll()
                    for (meal in allMeals) {
                        val mealName = meal.meal?.toLowerCase()
                        val ingredients =
                            meal.ingredients?.joinToString(separator = ",")?.toLowerCase()
                        // Check if the meal name or ingredients contain the search query
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
                    savedMealImages.clear()
                    savedAllMealsDetails.clear()

                    meals.forEach { meal ->
                        val mealItemView = layoutInflater.inflate(R.layout.meal_item, null)
                        val mealImage: ImageView = mealItemView.findViewById(R.id.mealImage)
                        val allMealsDetails: TextView =
                            mealItemView.findViewById(R.id.allMealsDetails)

                        // Load meal thumbnail image using Glide library
                        meal.mealThumb?.let { thumbnailUrl ->
                            Glide.with(this@SearchForMeals)
                                .load(thumbnailUrl)
                                .into(mealImage)
                            savedMealImages.add(thumbnailUrl)
                        }

                        val stb = StringBuilder()

                        // Append meal details to a StringBuilder
                        stb.append("\"Meal\":\"${meal.meal}\",\n")
                        stb.append("\"DrinkAlternate\":${meal.drinkAlternate},\n")
                        stb.append("\"Category\":\"${meal.category}\",\n")
                        stb.append("\"Area\":\"${meal.area}\",\n")
                        stb.append("\"Instructions\":\"${meal.instructions}\",\n")
                        stb.append("\"Tags\":${meal.tags},\n")
                        stb.append("\"Youtube\":\"${meal.youtube}\",\n")

                        meal.ingredients?.forEachIndexed { index, ingredient ->
                            stb.append("\"Ingredient${index + 1}\":\"$ingredient\",\n")
                        }

                        meal.measures?.forEachIndexed { index, measure ->
                            stb.append("\"Measure${index + 1}\":\"$measure\",\n")
                        }
                        stb.append("\n\n")

                        allMealsDetails.text = stb.toString().trim()
                        savedAllMealsDetails.add(allMealsDetails.text.toString())

                        mealsContainer.addView(mealItemView)
                    }
                }
            }
        }
    }
}