package com.example.mealmate

// Import required classes from Android framework and other packages.
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Declare a class named MainActivity that extends AppCompatActivity.
class MainActivity : AppCompatActivity() {

    private lateinit var db: MealDatabase

    // Override the onCreate() method of the superclass.
    override fun onCreate(savedInstanceState: Bundle?) {
        // Call the onCreate() method of the superclass with the savedInstanceState parameter.
        super.onCreate(savedInstanceState)
        // Set the layout of the activity using the R.layout.activity_main resource.
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            MealDatabase::class.java, "meal-db"
        ).fallbackToDestructiveMigration().build()

        // Declare a variable named addMealsDB and initialize it with a reference to the Button widget
        // with the ID addMealsToDB from the activity_main layout.
        var addMealsDB = findViewById<Button>(R.id.addMealsToDB)
        addMealsDB.isEnabled = true

        // Set a click listener on the addMealsDB button.
        addMealsDB.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val meal1 = Meals(
                    "Sweet and Sour Pork",
                    null,
                    "Pork",
                    "Chinese",
                    "Preparation\r\n1. Crack the egg into a bowl. Separate the egg white and yolk.\r\n\r\nSweet and Sour Pork\r\n2. Slice the pork tenderloin into ips.\r\n\r\n3. Prepare the marinade using a pinch of salt, one teaspoon of starch, two teaspoons of light soy sauce, and an egg white.\r\n\r\n4. Marinade the pork ips for about 20 minutes.\r\n\r\n5. Put the remaining starch in a bowl. Add some water and vinegar to make a starchy sauce.\r\n\r\nSweet and Sour Pork\r\nCooking Inuctions\r\n1. Pour the cooking oil into a wok and heat to 190\u00b0C (375\u00b0F). Add the marinated pork ips and fry them until they turn brown. Remove the cooked pork from the wok and place on a plate.\r\n\r\n2. Leave some oil in the wok. Put the tomato sauce and white sugar into the wok, and heat until the oil and sauce are fully combined.\r\n\r\n3. Add some water to the wok and thoroughly heat the sweet and sour sauce before adding the pork ips to it.\r\n\r\n4. Pour in the starchy sauce. Stir-fry all the ingredients until the pork and sauce are thoroughly mixed together.\r\n\r\n5. Serve on a plate and add some coriander for decoration.",
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
                        "1 tsp ",
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

                val meal2 = Meals(
                    "Chicken Marengo",
                    null,
                    "Chicken",
                    "French",
                    "Heat the oil in a large flameproof casserole dish and stir-fry the mushrooms until they start to soften. Add the chicken legs and cook briefly on each side to colour them a little.\r\nPour in the passata, crumble in the stock cube and stir in the olives. Season with black pepper \u2013 you shouldn\u2019t need salt. Cover and simmer for 40 mins until the chicken is tender. Sprinkle with parsley and serve with pasta and a salad, or mash and green veg, if you like.",
                    "https://www.themealdb.com/images/media/meals/qpxvuq1511798906.jpg",
                    null,
                    "https://www.youtube.com/watch?v=U33HYUr-0Fw",
                    listOf(
                        "Olive Oil",
                        "Mushrooms",
                        "Chicken Legs",
                        "Passata",
                        "Chicken Stock Cube",
                        "Black Olives",
                        "Parsley"
                    ),
                    listOf("1 tbs", "300g", "4", "500g", "1", "100g ", "Chopped"),
                    "https://www.bbcgoodfood.com/recipes/3146682/chicken-marengo",
                    null,
                    null,
                    null
                )
                val meal3 = Meals(
                    "Beef Banh Mi Bowls with Sriracha Mayo, Carrot & Pickled Cucumber",
                    null,
                    "Beef",
                    "Vietnamese",
                    "Add'l ingredients: mayonnaise, siracha\r\n\r\n1\r\n\r\nPlace rice in a fine-mesh sieve and rinse until water runs clear. Add to a small pot with 1 cup water (2 cups for 4 servings) and a pinch of salt. Bring to a boil, then cover and reduce heat to low. Cook until rice is tender, 15 minutes. Keep covered off heat for at least 10 minutes or until ready to serve.\r\n\r\n2\r\n\r\nMeanwhile, wash and dry all produce. Peel and finely chop garlic. Zest and quarter lime (for 4 servings, zest 1 lime and quarter both). Trim and halve cucumber lengthwise; thinly slice crosswise into half-moons. Halve, peel, and medium dice onion. Trim, peel, and grate carrot.\r\n\r\n3\r\n\r\nIn a medium bowl, combine cucumber, juice from half the lime, \u00bc tsp sugar (\u00bd tsp for 4 servings), and a pinch of salt. In a small bowl, combine mayonnaise, a pinch of garlic, a squeeze of lime juice, and as much sriracha as you\u2019d like. Season with salt and pepper.\r\n\r\n4\r\n\r\nHeat a drizzle of oil in a large pan over medium-high heat. Add onion and cook, stirring, until softened, 4-5 minutes. Add beef, remaining garlic, and 2 tsp sugar (4 tsp for 4 servings). Cook, breaking up meat into pieces, until beef is browned and cooked through, 4-5 minutes. Stir in soy sauce. Turn off heat; taste and season with salt and pepper.\r\n\r\n5\r\n\r\nFluff rice with a fork; stir in lime zest and 1 TBSP butter. Divide rice between bowls. Arrange beef, grated carrot, and pickled cucumber on top. Top with a squeeze of lime juice. Drizzle with sriracha mayo.",
                    "https://www.themealdb.com/images/media/meals/z0ageb1583189517.jpg",
                    null,
                    "",
                    listOf(
                        "Rice",
                        "Onion",
                        "Lime",
                        "Garlic Clove",
                        "Cucumber",
                        "Carrots",
                        "Ground Beef",
                        "Soy Sauce",
                        ""
                    ),
                    listOf("White", "1", "1", "3", "1", "3 oz ", "1 lb", "2 oz "),
                    "",
                    null,
                    null,
                    null
                )

                val meal4 = Meals(
                    "Leblebi Soup",
                    null,
                    "Vegetarian",
                    "Tunisian",
                    "Heat the oil in a large pot. Add the onion and cook until translucent.\r\nDrain the soaked chickpeas and add them to the pot together with the vegetable stock. Bring to the boil, then reduce the heat and cover. Simmer for 30 minutes.\r\nIn the meantime toast the cumin in a small ungreased frying pan, then grind them in a mortar. Add the garlic and salt and pound to a fine paste.\r\nAdd the paste and the harissa to the soup and simmer until the chickpeas are tender, about 30 minutes.\r\nSeason to taste with salt, pepper and lemon juice and serve hot.",
                    "https://www.themealdb.com/images/media/meals/x2fw9e1560460636.jpg",
                    "Soup",
                    "https://www.youtube.com/watch?v=BgRifcCwinY",
                    listOf(
                        "Olive Oil",
                        "Onion",
                        "Chickpeas",
                        "Vegetable Stock",
                        "Cumin",
                        "Garlic",
                        "Salt",
                        "Harissa Spice",
                        "Pepper",
                        "Lime"
                    ),
                    listOf(
                        "2 tbs",
                        "1 medium finely diced",
                        "250g",
                        "1.5L",
                        "1 tsp ",
                        "5 cloves",
                        "1/2 tsp",
                        "1 tsp ",
                        "Pinch",
                        "1/2 "
                    ),
                    "http://allrecipes.co.uk/recipe/43419/leblebi--tunisian-chickpea-soup-.aspx",
                    null,
                    null,
                    null
                )

                db.mealDao().insert(meal1)
                db.mealDao().insert(meal2)
                db.mealDao().insert(meal3)
                db.mealDao().insert(meal4)

            }
            Toast.makeText(this, "Meals added to database", Toast.LENGTH_SHORT).show()
            addMealsDB.setBackgroundColor(Color.GRAY)
            addMealsDB.setTextColor(Color.BLACK)
            addMealsDB.isEnabled = false
        }

        var addMoreMealsDB = findViewById<Button>(R.id.addMoreMealsDB)

        // Set a click listener on the addMoreMealsDB button.
        addMoreMealsDB.setOnClickListener {
            // Create an Intent object with the current activity context and the class of the destination activity.
            val addMeals = Intent(this, AddMeals::class.java)
            // Start the destination activity with the Intent object.
            startActivity(addMeals)
        }
    }
}

