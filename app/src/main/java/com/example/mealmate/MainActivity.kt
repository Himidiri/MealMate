package com.example.mealmate

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private var addMealsDBClicked: Boolean = false

    var addMealsDB: Button? = null
    var searchMealsByIngredient: Button? = null
    var searchForMeals: Button? = null
    var searchMealsFromWebservice: Button? = null
    private lateinit var mealsDao: MealsDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the MealDatabase and MealsDao
        val db = Room.databaseBuilder(this, MealDatabase::class.java, "meal-db").build()
        mealsDao = db.mealDao()

        // Initialize the buttons
        addMealsDB = findViewById(R.id.addMealsToDB)
        searchMealsByIngredient = findViewById(R.id.searchMealsByIngredients)
        searchForMeals = findViewById(R.id.searchMeals)
        searchMealsFromWebservice =findViewById(R.id.searchMealsBySubstring)

        // Enable the addMealsDB button
        addMealsDB?.isEnabled = true

        // Set click listeners for the buttons
        addMealsDBButton()
        searchMealsByIngredientButton()
        searchForMealsButton()
        searchMealsFromWebServiceButton()

        // Restore the state of the addMealsDBClicked flag
        if (savedInstanceState != null) {
            addMealsDBClicked = savedInstanceState.getBoolean("addMealsDBClicked")
            if (addMealsDBClicked) {
                disableAddMealsDBButton()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("addMealsDBClicked", addMealsDBClicked)
    }

    // Disable the addMealsDB button
    private fun disableAddMealsDBButton() {
        addMealsDB?.apply {
            isEnabled = false
            setBackgroundColor(Color.GRAY)
            setTextColor(Color.BLACK)
        }
    }

    // Handle the click event for the addMealsDB button
    private fun addMealsDBButton() {
        addMealsDB?.setOnClickListener {
            // Use coroutines to perform the database insertion on a separate thread
            runBlocking {
                launch {
                    // Create Meals objects representing different meals
                    val meal1 = Meals(
                        0,
                        "Sweet and Sour Pork",
                        null,
                        "Pork",
                        "Chinese",
                        "Preparation: ...",
                        "https://www.themealdb.com/images/media/meals/1529442316.jpg",
                        "Sweet",
                        "https://www.youtube.com/watch?v=mdaBIhgEAMo",
                        listOf(
                            "Pork",
                            "Egg",
                            "Water",
                            "Salt",
                            "Sugar",
                            "Soy Sauce",
                            "Starch",
                            "Tomato Puree",
                            "Vinegar",
                            "Coriander"
                        ),
                        listOf(
                            "200g",
                            "1",
                            "Dash",
                            "1/2 tsp",
                            "1 tsp",
                            "10g",
                            "10g",
                            "30g",
                            "10g",
                            "Dash"
                        ),
                        null,
                        null,
                        null,
                        null
                    )
                    // Insert the meals into the database
                    mealsDao.insertMeals(meal1)
                }
            }
            // Display a toast message to indicate the meals have been added to the database
            Toast.makeText(this, "Meals Added To Database", Toast.LENGTH_SHORT).show()
            // Update the flag and disable the addMealsDB button
            addMealsDBClicked = true
            disableAddMealsDBButton()
        }
    }

    // Handle the click event for the searchMealsByIngredient button
    private fun searchMealsByIngredientButton() {
        searchMealsByIngredient?.setOnClickListener {
            // Create an intent to navigate to the SearchMealsByIngredient activity
            val mealsByIngredient = Intent(this, SearchMealsByIngredient::class.java)
            startActivity(mealsByIngredient)
        }
    }

    // Handle the click event for the searchForMeals button
    private fun searchForMealsButton() {
        searchForMeals?.setOnClickListener {
            // Create an intent to navigate to the SearchForMeals activity
            val searchMeals = Intent(this, SearchForMeals::class.java)
            startActivity(searchMeals)
        }
    }

    private fun searchMealsFromWebServiceButton() {
        searchMealsFromWebservice?.setOnClickListener {
            // Create an intent to navigate to the SearchForMeals activity
            val  searchMealsFromWeb = Intent(this, SearchMealsFromWebService::class.java)
            startActivity(searchMealsFromWeb)
        }
    }
}