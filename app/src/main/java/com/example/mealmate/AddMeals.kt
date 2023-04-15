package com.example.mealmate

// Import required classes from Android framework and other packages.
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Declare a class named AddMeals that extends AppCompatActivity.
class AddMeals : AppCompatActivity() {

    private lateinit var db: MealDatabase

    // Declare a private lateinit variable named container of type LinearLayout.
    private lateinit var container: LinearLayout

    // Override the onCreate() method of the superclass.
    override fun onCreate(savedInstanceState: Bundle?) {
        // Call the onCreate() method of the superclass with the savedInstanceState parameter.
        super.onCreate(savedInstanceState)
        // Set the layout of the activity using the R.layout.activity_add_meals resource.
        setContentView(R.layout.activity_add_meals)

        // Initialize the container variable with a reference to the LinearLayout widget with the ID container from the activity_add_meals layout.
        container = findViewById(R.id.container)

        // Initialize the home variable with a reference to the ImageView widget with the ID homeBtn from the activity_add_meals layout.
        var home = findViewById<ImageView>(R.id.homeBtn)
        // Set a click listener on the home variable to create an Intent object that navigates to the MainActivity and start that activity.
        home.setOnClickListener {
            val homePage = Intent(this, MainActivity::class.java)
            startActivity(homePage)
        }

        // Initialize the addIngredient variable with a reference to the ImageView widget with the ID addIngredientBtn from the activity_add_meals layout.
        var addIngredient = findViewById<ImageView>(R.id.addIngredientBtn)
        // Set a click listener on the addIngredient variable to call the addRow() function.
        addIngredient.setOnClickListener {
            addRow()
        }

        // Initialize the addIngredient variable with a reference to the ImageView widget with the ID addIngredientBtn from the activity_add_meals layout.
        var saveMeals = findViewById<Button>(R.id.save)

        // Get references to the input fields and assign their values to corresponding variables.
        val meal = findViewById<TextInputLayout>(R.id.meal_name).editText?.text.toString()
        val drinkAlternate = findViewById<TextInputLayout>(R.id.drinkAlternate).editText?.text.toString()
        val category = findViewById<TextInputLayout>(R.id.category).editText?.text.toString()
        val area = findViewById<TextInputLayout>(R.id.area).editText?.text.toString()
        val instructions = findViewById<TextInputLayout>(R.id.instructions).editText?.text.toString()
        val mealThumb = findViewById<TextInputLayout>(R.id.meal_thumb).editText?.text.toString()
        val tags = findViewById<TextInputLayout>(R.id.tags).editText?.text.toString()
        val youtube = findViewById<TextInputLayout>(R.id.youtube).editText?.text.toString()

        // Set a click listener on the addIngredient variable to call the addRow() function.
        saveMeals.setOnClickListener {
            val meal = Meals(0,meal, drinkAlternate, category, area, instructions, mealThumb, tags, youtube, ingredients = getIngredientList(), measures = getMeasureList() ,"","","","")

            GlobalScope.launch {
                db.mealDao().insert(meal)
            }

        }
    }

    // Define a private function named addRow().
    private fun addRow() {

        // Get an instance of LayoutInflater using the getSystemService() method.
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // Inflate the add_ingredient_row layout using the inflater object and assign it to rowView variable.
        val rowView = inflater.inflate(R.layout.add_ingredient_row, null)

        // Add the rowView to the container LinearLayout using the addView() method.
        container.addView(rowView, container.childCount)

        // Get references to the TextInputLayout and ImageView widgets from the add_ingredient_row layout and assign them to corresponding variables.
        val addIngredients = rowView.findViewById<TextInputLayout>(R.id.editTextIngredient)
        val addMeasure = rowView.findViewById<TextInputLayout>(R.id.editTextMeasure)
        val cancel = rowView.findViewById<ImageView>(R.id.buttonCancel)

        // Set a click listener on the cancel ImageView to remove the rowView from the container LinearLayout using the removeView() method.
        cancel.setOnClickListener {
            container.removeView(rowView)
        }

        // Get a reference to the ScrollView widget with the ID scrollView from the activity_add_meals layout.
        val scrollView = findViewById<ScrollView>(R.id.scrollView)

        // Use the postDelayed() method to delay the scrolling by 100 milliseconds.
        scrollView.postDelayed({
            // Scroll to the bottom of the scrollView using the fullScroll() method.
            scrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }, 100)
    }

    private fun getIngredientList(): List<String> {
        val ingredients = mutableListOf<String>()
        for (i in 0 until container.childCount) {
            val rowView = container.getChildAt(i)
            val ingredient = rowView.findViewById<TextInputLayout>(R.id.editTextIngredient).editText?.text.toString()
            ingredients.add(ingredient)
        }
        return ingredients
    }

    private fun getMeasureList(): List<String> {
        val measures = mutableListOf<String>()
        for (i in 0 until container.childCount) {
            val rowView = container.getChildAt(i)
            val measure = rowView.findViewById<TextInputLayout>(R.id.editTextMeasure).editText?.text.toString()
            measures.add(measure)
        }
        return measures
    }

}
