package com.example.mealmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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

        db = MealDatabase.getDatabase(this)

        anyString = findViewById(R.id.anyString)
        search = findViewById(R.id.search)
        mealsContainer = findViewById(R.id.mealsContainer)

        searchButton()

        savedInstanceState?.let {
            savedMealImages = it.getStringArrayList("mealImages")?.toMutableList() ?: mutableListOf()
            savedAllMealsDetails = it.getStringArrayList("allMealsDetails")?.toMutableList() ?: mutableListOf()
        }

        savedMealImages.forEachIndexed { index, imageUrl ->
            val mealItemView = layoutInflater.inflate(R.layout.meal_item, null)
            val mealImage: ImageView = mealItemView.findViewById(R.id.mealImage)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("mealImages", ArrayList(savedMealImages))
        outState.putStringArrayList("allMealsDetails", ArrayList(savedAllMealsDetails))
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
                    savedMealImages.clear()
                    savedAllMealsDetails.clear()

                    meals.forEach { meal ->
                        val mealItemView = layoutInflater.inflate(R.layout.meal_item, null)
                        val mealImage: ImageView = mealItemView.findViewById(R.id.mealImage)
                        val allMealsDetails: TextView = mealItemView.findViewById(R.id.allMealsDetails)

                        meal.mealThumb?.let { thumbnailUrl ->
                            Glide.with(this@SearchForMeals)
                                .load(thumbnailUrl)
                                .into(mealImage)
                            savedMealImages.add(thumbnailUrl)
                        }

                        val stb = StringBuilder()

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
