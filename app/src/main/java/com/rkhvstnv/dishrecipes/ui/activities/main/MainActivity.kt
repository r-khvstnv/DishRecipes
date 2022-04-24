package com.rkhvstnv.dishrecipes.ui.activities.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rkhvstnv.dishrecipes.DishApplication
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.ActivityMainBinding
import com.rkhvstnv.dishrecipes.di.viewmodel.ViewModelFactory
import com.rkhvstnv.dishrecipes.utils.appComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels{
        vmFactory
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.findNavController()

        navController.addOnDestinationChangedListener{
            controller, destination, arguments ->

            when(destination.id){
                R.id.navigation_dish_details -> hideNavView()
                R.id.navigation_add_update_dish -> hideNavView()
                else -> showNavView()
            }
        }

        binding.navView.setupWithNavController(navController)

        viewModel.test.observe(this){
            st ->
            st.let {
                Toast.makeText(this, st, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun hideNavView(){
        binding.navView.visibility = View.GONE
    }

    fun showNavView(){
        binding.navView.visibility = View.VISIBLE
    }

}