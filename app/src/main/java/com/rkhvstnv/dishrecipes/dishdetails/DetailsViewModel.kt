package com.rkhvstnv.dishrecipes.dishdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rkhvstnv.dishrecipes.app.db.DishRepository
import com.rkhvstnv.dishrecipes.app.models.Dish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: DishRepository
    ): ViewModel() {

    var dish: LiveData<Dish>? = null

    fun setDish(id: Int){
        dish = repository.getDishById(id = id).asLiveData()
    }

    fun flipDishFavoriteState(dish: Dish): Dish {
        dish.isFavoriteDish = !dish.isFavoriteDish
        return dish
    }
    fun updateDishData(dish: Dish) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateDish(dish = dish)
    }
    fun deleteDishData(dish: Dish) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteDish(dish = dish)
    }
}