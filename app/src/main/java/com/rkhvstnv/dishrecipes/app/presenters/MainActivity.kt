package com.rkhvstnv.dishrecipes.app.presenters

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.ActivityMainBinding
import com.rkhvstnv.dishrecipes.utils.Constants
import com.rkhvstnv.dishrecipes.utils.NotificationWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.findNavController()

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id){
                R.id.navigation_details -> hideNavView()
                R.id.navigation_add_update -> hideNavView()
                else -> showNavView()
            }
        }

        binding.navView.setupWithNavController(navController)

        setWorkManager()
        notificationClickHandler()
    }

    private fun hideNavView(){
        binding.navView.visibility = View.GONE
    }

    private fun showNavView(){
        binding.navView.visibility = View.VISIBLE
    }

    /**Ensure that wor is deferred until optimal conditions are met*/
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .setRequiresCharging(false)
        .setRequiresBatteryNotLow(true)
        .build()
    private val notificationWorkRequest: PeriodicWorkRequest =
        PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
    private fun setWorkManager(){
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            Constants.NOTIFICATION_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            notificationWorkRequest
        )
    }

    private fun notificationClickHandler(){
        if (intent.hasExtra(Constants.NOTIFICATION_ID)){
            binding.navView.selectedItemId = R.id.navigation_random
        }
    }
}