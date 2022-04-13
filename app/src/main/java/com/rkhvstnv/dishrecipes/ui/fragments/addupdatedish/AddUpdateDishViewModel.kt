package com.rkhvstnv.dishrecipes.ui.fragments.addupdatedish

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.model.DishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddUpdateDishViewModelFactory(private val repository: DishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AddUpdateDishViewModel(repository = repository) as T
    }
}

class AddUpdateDishViewModel(private val repository: DishRepository) : ViewModel() {
    //Bitmap received after user add it from gallery. Should be assign to Null in onDestroyView
    var dishBitmap: Bitmap? = null
    //Value assigned only after new bitmap was saved
    var imagePath: String = ""
    //Received using dishId from DishDetailsFragment. Should be assign to Null in onDestroyView
    var tmpDish: LiveData<Dish>? = null

    fun insert(dish: Dish) = viewModelScope.launch {
        repository.insertDishData(dish = dish)
    }

    fun assignTmpDish(dishId: Int){
        tmpDish = repository.getDishById(dishId)
    }

    fun flipDishFavouriteState(dish: Dish): Dish{
        dish.isFavoriteDish = !dish.isFavoriteDish
        return dish
    }

    fun updateDishModel(dish: Dish) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateDishData(dish = dish)
    }
}