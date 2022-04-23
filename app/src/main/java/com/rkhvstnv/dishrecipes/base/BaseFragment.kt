package com.rkhvstnv.dishrecipes.base

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.ui.activities.main.MainActivity
import com.rkhvstnv.dishrecipes.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

open class BaseFragment: Fragment() {

    private fun navigateToFragment(navFragmentId: Int){
        val navHost = findNavController()
        navHost.navigate(navFragmentId)
    }

    /**Navigate to AllDishesFragment destroying Fragment, which made call*/
    fun navigateToAllDishes(fragment: Fragment){
        parentFragmentManager
            .beginTransaction()
            .remove(fragment)
            .commit()
        navigateToFragment(R.id.navigation_all_dishes)
    }

    fun showSnackBarPermissionError(){
        val sb = Snackbar.make(
            activity?.findViewById(android.R.id.content)!!,
            resources.getString(R.string.st_permission_error),
            Snackbar.LENGTH_LONG
        )
        sb.setAction(resources.getString(R.string.st_settings)){
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", activity?.packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        sb.show()
    }

    fun showSnackBarErrorMessage(message: String){
        val sb = Snackbar.make(
            activity?.findViewById(android.R.id.content)!!,
            message,
            Snackbar.LENGTH_LONG)

        sb.show()
    }

    fun showSnackBarPositiveMessage(message: String){
        val sb = Snackbar.make(
            activity?.findViewById(android.R.id.content)!!,
            message,
            Snackbar.LENGTH_LONG
        )
        sb.show()
    }

    /**Delete dishImage from internal storage*/
    fun deleteFile(path: String, source: String){
        if (source != Constants.IMAGE_SOURCE_NETWORK){
            lifecycleScope.launch(Dispatchers.IO){
                val file = File(path)
                try {
                    file.delete()
                } catch (e: IOException){
                    e.printStackTrace()
                }
            }
        }
    }

    /**Hiding bottomNavView prevents wrong navigation.
     * Otherwise user will see old dishData, when he want to create new dish */
    fun hideNavigationView(){
        (activity as MainActivity).hideNavView()
    }
}