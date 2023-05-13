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


class SearchMealsByIngredient : AppCompatActivity() {

    private lateinit var db: MealDatabase
    private val meals = mutableListOf<Meals>()
    private lateinit var searchIngredient: EditText
    private lateinit var mealsDetails: TextView

    var retrieveMeals: Button? = null
    var saveMealsToDB: Button? = null
    val stb = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_meals_by_ingredient)

        db = MealDatabase.getDatabase(this)

        searchIngredient = findViewById(R.id.searchIngredient)
        mealsDetails = findViewById(R.id.mealsDetails)
        retrieveMeals = findViewById(R.id.retrieveMeals)
        saveMealsToDB = findViewById(R.id.saveMealsToDatabase)

        retrieveMealsButton()
        saveMealsToDBButton()


    }

    private fun retrieveMealsButton() {
        retrieveMeals?.setOnClickListener {
            if (searchIngredient.text.isBlank()) {
                Toast.makeText(this, "Please enter an ingredient", Toast.LENGTH_SHORT).show()
            } else {
                val ingredient = searchIngredient.text.toString()
                val url = URL("https://www.themealdb.com/api/json/v1/1/filter.php?i=$ingredient")
                val con: HttpURLConnection = url.openConnection() as HttpURLConnection
                runBlocking {
                    launch {
                        withContext(Dispatchers.IO) {
                            val bf = BufferedReader(InputStreamReader(con.inputStream))
                            var line: String? = bf.readLine()
                            while (line != null) {
                                stb.append(line + "\n")
                                line = bf.readLine()
                            }
                            bf.close()
                            parseJSON(stb)
                            stb.clear()
                        }
                    }
                }
            }
        }
    }

    private fun parseJSON(stb: StringBuilder) {
        val json = JSONObject(stb.toString())
        val allmeals = StringBuilder()
        val mealsArray: JSONArray? = json.optJSONArray("meals")

        if (mealsArray == null) {
            allmeals.append("No meals found")
        } else {
            for (i in 0 until mealsArray.length()) {
                val meal: JSONObject = mealsArray[i] as JSONObject
                val mealname = meal["strMeal"] as String
                //   allmeals.append("\n ${i + 1}) \n\n")
                allmeals.append("\"Meal\" : \"$mealname\" , \n")
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

                val mealJson = JSONObject(sb.toString())
                val mealArray: JSONArray = mealJson.getJSONArray("meals")
                val mealObject: JSONObject = mealArray[0] as JSONObject

                // Append all relevant data to the StringBuilder object
                allmeals.append("\"Drink Alternate\" : \"${mealObject["strDrinkAlternate"]}\" ,\n")
                allmeals.append("\"Category\" : \"${mealObject["strCategory"]}\" ,\n")
                allmeals.append("\"Area\" : \"${mealObject["strArea"]}\" ,\n")
                allmeals.append("\"Instructions\" : \"${mealObject["strInstructions"]}\" ,\n")
                allmeals.append("\"Tags\" : \"${mealObject["strTags"]}\" ,\n")
                allmeals.append("\"YouTube\" : \"${mealObject["strYoutube"]}\" ,\n")
                //  allmeals.append("Ingredients :\n")
                // Append all ingredients and their measurements to the StringBuilder object
                val ingredientlist = mutableListOf<String>()
                val measureslist = mutableListOf<String>()
                for (j in 1..20) {
                    val ingredient = mealObject["strIngredient$j"]
                    if (ingredient != "") {
                        allmeals.append("\"Ingredients$j\" : \"$ingredient\" ,\n")
                        ingredientlist.add(ingredient.toString())
                    }
                }
                for (k in 1..20) {
                    val measure = mealObject["strMeasure$k"]
                    if (measure != "") {
                        allmeals.append("\"Measure$k\" : \"$measure\" ,\n")
                        //  allmeals.append("$ingredient: $measure\n")
                        measureslist.add(measure.toString())
                    }
                }
                /*   allmeals.append("\"Source\" : \"${mealObject["strSource"]}\" ,\n")
                allmeals.append("\"ImageSource\" : \"${mealObject["strImageSource"]}\" ,\n")
                allmeals.append("\"CreativeCommonsConfirmed\" : \"${mealObject["strCreativeCommonsConfirmed"]}\" ,\n")
                allmeals.append("\"dateModified\" : \"${mealObject["dateModified"]}\" ,\n") */

                allmeals.append("\n\n")
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
        runOnUiThread {
            mealsDetails.text = allmeals.toString()
        }
    }

    private fun saveMealsToDBButton() {
        saveMealsToDB?.setOnClickListener {
            if (meals.size > 0) {
                Toast.makeText(this, "Meals Save in DataBase", Toast.LENGTH_SHORT).show()
                for (meal in meals) {
                    runBlocking {
                        launch {
                            withContext(Dispatchers.IO) {
                                db.mealDao().insertMeals(meal)
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "No meals to save", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
