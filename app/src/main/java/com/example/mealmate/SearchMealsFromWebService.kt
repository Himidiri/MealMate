package com.example.mealmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
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

class SearchMealsFromWebService : AppCompatActivity() {

    // Declare properties

    // A list to store meals retrieved from the web service
    private val meals = mutableListOf<Meals>()

    // UI elements
    private lateinit var searchFromWebText: EditText
    private lateinit var searchFromWebResult: TextView
    private lateinit var searchFromWebBtn: Button

    // A StringBuilder to accumulate API response
    private val stb = StringBuilder()

    // Activity lifecycle method that is called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_meals_from_web_service)

        // Get references to UI elements
        searchFromWebBtn = findViewById(R.id.searchFromWebBtn)
        searchFromWebText = findViewById(R.id.searchFromWeb)
        searchFromWebResult = findViewById(R.id.mealsDetailsFromWeb)

        // Set click listener for the search button
        searchFromWebBtn.setOnClickListener {
            searchFromWeb()
        }
    }

    // Retrieve meals from the web service
    private fun searchFromWeb() {
        // Check if the search from web EditText is blank
        if (searchFromWebText.text.isBlank()) {
            // Display a toast message if the search query is empty
            Toast.makeText(this, "Please Enter Something", Toast.LENGTH_SHORT).show()
        } else {
            // Retrieve meals from the API
            val searchQuery = searchFromWebText.text.toString()
            val url = URL("https://www.themealdb.com/api/json/v1/1/search.php?s=$searchQuery")
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection
            runBlocking {
                launch {
                    withContext(Dispatchers.IO) {
                        // Read the API response
                        val bf = BufferedReader(InputStreamReader(con.inputStream))
                        var line: String? = bf.readLine()
                        while (line != null) {
                            stb.append(line + "\n")
                            line = bf.readLine()
                        }
                        bf.close()
                        // Parse the JSON response and update the UI
                        parseJSON(stb)
                        stb.clear()
                    }
                }
            }
        }
    }

    // Parse JSON response and update UI
    private fun parseJSON(stb: StringBuilder) {
        val json = JSONObject(stb.toString())
        val allmeals = StringBuilder()
        val mealsArray: JSONArray? = json.optJSONArray("meals")

        // Check if meals were found for the searchQuery
        if (mealsArray == null) {
            // If no meals found, display a message
            allmeals.append("No Meals Found")
        } else {
            // Iterate over the meals and retrieve their details
            for (i in 0 until mealsArray.length()) {
                val meal: JSONObject = mealsArray[i] as JSONObject
                val mealname = meal["strMeal"] as String

                allmeals.append("\"Meal\" : \"$mealname\" , \n")

                // Retrieve details of the meal
                val url = URL("https://www.themealdb.com/api/json/v1/1/search.php?s=$mealname")
                val con: HttpURLConnection = url.openConnection() as HttpURLConnection
                val sb = StringBuilder()
                runBlocking {
                    launch {
                        withContext(Dispatchers.IO) {
                            val bf = BufferedReader(InputStreamReader(con.inputStream))
                            var line: String? = bf.readLine()
                            while (line != null) {
                                sb.append(line + "\n")
                                line = bf.readLine()
                            }
                            bf.close()
                        }
                    }
                }

                // Parse the JSON response for meal details
                val mealJson = JSONObject(sb.toString())
                val mealArray: JSONArray = mealJson.getJSONArray("meals")
                val mealObject: JSONObject = mealArray[0] as JSONObject

                // Append meal details to the StringBuilder
                allmeals.append("\"Drink Alternate\" : \"${mealObject["strDrinkAlternate"]}\" ,\n")
                allmeals.append("\"Category\" : \"${mealObject["strCategory"]}\" ,\n")
                allmeals.append("\"Area\" : \"${mealObject["strArea"]}\" ,\n")
                allmeals.append("\"Instructions\" : \"${mealObject["strInstructions"]}\" ,\n")
                allmeals.append("\"MealThumb\" : \"${mealObject["strMealThumb"]}\" ,\n")
                allmeals.append("\"Tags\" : \"${mealObject["strTags"]}\" ,\n")
                allmeals.append("\"YouTube\" : \"${mealObject["strYoutube"]}\" ,\n")

                // Retrieve ingredient list
                val ingredientlist = mutableListOf<String>()
                for (j in 1..20) {
                    val ingredient = mealObject["strIngredient$j"]
                    if (ingredient != "") {
                        allmeals.append("\"Ingredients$j\" : \"$ingredient\" ,\n")
                        ingredientlist.add(ingredient.toString())
                    }
                }

                // Retrieve measure list
                val measureslist = mutableListOf<String>()
                for (k in 1..20) {
                    val measure = mealObject["strMeasure$k"]
                    if (measure != "") {
                        allmeals.append("\"Measure$k\" : \"$measure\" ,\n")
                        measureslist.add(measure.toString())
                    }
                }

                allmeals.append("\n\n")

                // Create Meals object and add it to the list
                meals.add(
                    Meals(
                        mealObject["idMeal"].toString().toInt(),
                        mealObject["strMeal"].toString(),
                        mealObject["strDrinkAlternate"].toString(),
                        mealObject["strCategory"].toString(),
                        mealObject["strArea"].toString(),
                        mealObject["strInstructions"].toString(),
                        mealObject["strMealThumb"].toString(),
                        mealObject["strTags"].toString(),
                        mealObject["strYoutube"].toString(),
                        ingredientlist,
                        measureslist,
                        mealObject["strSource"].toString(),
                        mealObject["strImageSource"].toString(),
                        mealObject["strCreativeCommonsConfirmed"].toString(),
                        mealObject["dateModified"].toString()
                    )
                )
            }
        }

        // Update the UI on the main thread
        runOnUiThread {
            searchFromWebResult.text = allmeals.toString()
        }
    }

// Other lifecycle methods
    override fun onDestroy() {
        super.onDestroy()
        meals.clear()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("data", searchFromWebResult.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchFromWebResult.text = savedInstanceState.getString("data")
    }
}