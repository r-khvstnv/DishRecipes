package com.rkhvstnv.dishrecipes.ui.fragments

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.ui.fragments.alldishes.AllDishesFragment
import com.rkhvstnv.dishrecipes.utils.Constants

open class BaseFragment: Fragment() {
    fun navigateToFragment(navFragmentId: Int){
        val navHost = findNavController()
        navHost.navigate(navFragmentId)
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
            Snackbar.LENGTH_LONG
        )

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

    //todo for test deleting
    private fun delDir(){
        val wrapper = ContextWrapper(context?.applicationContext)
        val file = wrapper.getDir(Constants.IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file.deleteRecursively()
    }
}