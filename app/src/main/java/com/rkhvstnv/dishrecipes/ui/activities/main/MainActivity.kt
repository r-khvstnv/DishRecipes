package com.rkhvstnv.dishrecipes.ui.activities.main

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = getNavController()

        if (navController != null) {
            binding.navView.setupWithNavController(navController)

            navController.addOnDestinationChangedListener{
                _, destination, _ ->

                if (destination.id == R.id.dish_details_fragment){
                    hideNavView()
                } else{
                    showNavView()
                }
            }
        }

    }

    private fun getNavController(): NavController? {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
        return navHostFragment?.findNavController()
    }

    private fun showNavView(){
        binding.navView.clearAnimation()
        binding.navView.animate().translationY(0f).duration = 300
    }

    private fun hideNavView(){
        binding.navView.clearAnimation()
        binding.navView.animate().translationY(binding.navView.height.toFloat()).duration = 300
    }

}