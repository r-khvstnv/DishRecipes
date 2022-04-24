package com.rkhvstnv.dishrecipes.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.findNavController()

        navController.addOnDestinationChangedListener{
                _, destination, _ ->

            when(destination.id){
                R.id.navigation_dish_details -> hideNavView()
                R.id.navigation_add_update_dish -> hideNavView()
                else -> showNavView()
            }
        }

        binding.navView.setupWithNavController(navController)
    }

    private fun hideNavView(){
        binding.navView.visibility = View.GONE
    }

    private fun showNavView(){
        binding.navView.visibility = View.VISIBLE
    }

}