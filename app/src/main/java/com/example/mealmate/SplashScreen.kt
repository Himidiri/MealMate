package com.example.mealmate

// Import required classes from Android framework and other packages.
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

// Declare a class named SplashScreen that extends AppCompatActivity.
class SplashScreen : AppCompatActivity() {

    // Declare a private lateinit variable named image of type ImageView.
    private lateinit var image: ImageView

    // Override the onCreate() method of the superclass.
    override fun onCreate(savedInstanceState: Bundle?) {
        // Call the onCreate() method of the superclass with the savedInstanceState parameter.
        super.onCreate(savedInstanceState)
        // Set the layout of the activity using the R.layout.splash_screen_activity resource.
        setContentView(R.layout.splash_screen_activity)

        // Initialize the image variable with a reference to the ImageView widget with the ID logo from the splash_screen_activity layout.
        image = findViewById(R.id.logo)

        // Set the alpha value of the image to 0.
        image.alpha=0f

        // Use the animate() method of the image to animate the alpha value from 0 to 1 over a duration of 3000 milliseconds (3 seconds).
        image.animate().setDuration(2500).alpha(1f).withEndAction{
            // When the animation ends, create an Intent object with the current activity context and the class of the destination activity.
            val intent = Intent(this,MainActivity::class.java)
            // Start the destination activity with the Intent object.
            startActivity(intent)
            // Use the overridePendingTransition() method to specify the animation to use when transitioning to the destination activity.
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            // Call the finish() method to close the current activity.
            finish()
        }
    }
}
