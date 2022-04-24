package com.rkhvstnv.dishrecipes.ui.fragments.addupdatedish

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.base.BaseViewModel
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.database.DishRepository
import javax.inject.Inject

class AddUpdateDishViewModel @Inject constructor(
    private val repository: DishRepository
    ): BaseViewModel(repository = repository) {

    //Bitmap received after user add it from gallery. Should be assign to Null in onDestroyView
    var dishBitmap: Bitmap? = null
    //Value assigned only after new bitmap was saved
    var imagePath: String = ""
    //Received using dishId from DishDetailsFragment. Should be assign to Null in onDestroyView
    var tmpDish: LiveData<Dish>? = null

    /**Receive certain dish for displaying/updating*/
    fun assignTmpDish(dishId: Int){
        tmpDish = repository.getDishById(dishId).asLiveData()
    }
}