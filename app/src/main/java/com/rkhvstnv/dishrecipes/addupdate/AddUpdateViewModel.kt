package com.rkhvstnv.dishrecipes.addupdate

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.app.presenter.BaseViewModel
import com.rkhvstnv.dishrecipes.app.domain.Dish
import com.rkhvstnv.dishrecipes.app.data.DishRepository
import javax.inject.Inject

class AddUpdateViewModel @Inject constructor(
    private val repository: DishRepository
    ): BaseViewModel(repository = repository) {

    //Bitmap received after user add it from gallery. Should be assign to Null in onDestroyView
    var dishBitmap: Bitmap? = null
    //Value assigned only after new bitmap was saved
    var imagePath: String = ""
    //Received using dishId from DetailsFragment. Should be assign to Null in onDestroyView
    var tmpDish: LiveData<Dish>? = null

    /**Receive certain dish for displaying/updating*/
    fun assignTmpDish(dishId: Int){
        tmpDish = repository.getDishById(dishId).asLiveData()
    }
}