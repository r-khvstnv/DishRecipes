package com.rkhvstnv.dishrecipes.ui.activities.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rkhvstnv.dishrecipes.DishApplication
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.ActivityMainBinding
import com.rkhvstnv.dishrecipes.di.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var vmFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels{
        vmFactory
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //todo these
        (application as DishApplication).appComponent.viewModelComponent().create().inject(this)
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = getNavController()

        if (navController != null) {
            binding.navView.setupWithNavController(navController)

            navController.addOnDestinationChangedListener{
                _, destination, _ ->

                if (destination.id == R.id.navigation_dish_details){
                    hideNavView()
                } else{
                    showNavView()
                }
            }
        }

        viewModel.test.observe(this){
            st ->
            st.let {
                Toast.makeText(this, st, Toast.LENGTH_LONG).show()
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

    //Must be public, due to it's used in AddUpdateDishFragment
    fun hideNavView(){
        binding.navView.clearAnimation()
        binding.navView.animate().translationY(binding.navView.height.toFloat()).duration = 300
    }

}