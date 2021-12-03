package com.example.s185178lykkehjuletv6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.s185178lykkehjuletv6.databinding.ActivityMainBinding

//Gruppe G2 hvor vi har brugt meget tid på gruppe opgaven

// My 6th Github file, others has been deleted because they kept crashing
// and give me errors for no reason, so couldn't figure out why so had to delete and start over

/*Code status:
- Wheel have been implemented but it only picks from 4 values because the 9 other values is simulated in another loop
when it has been tried to
*/

/**
 * Taken from word app guide https://github.com/google-developer-training/android-basics-kotlin-words-app/tree/activities
 */
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController
        // Make sure actions in the ActionBar get propagated to the NavController
        setupActionBarWithNavController(navController)

    }

    /**
     * Enables back button support. Simply navigates one element up on the stack.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
