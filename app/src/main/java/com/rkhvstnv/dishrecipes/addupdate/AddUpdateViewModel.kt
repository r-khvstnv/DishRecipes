package com.rkhvstnv.dishrecipes.addupdate

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.app.models.Dish
import com.rkhvstnv.dishrecipes.app.db.DishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddUpdateViewModel @Inject constructor(
    private val repository: DishRepository
    ): ViewModel() {

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

    fun insertDishData(dish: Dish) = viewModelScope.launch {
        repository.insertDish(dish = dish)
    }
    fun updateDishData(dish: Dish) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateDish(dish = dish)
    }
}