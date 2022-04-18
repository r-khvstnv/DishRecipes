package com.rkhvstnv.dishrecipes.bases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkhvstnv.dishrecipes.models.Dish
import com.rkhvstnv.dishrecipes.database.DishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel(private val repository: DishRepository): ViewModel() {
    //style for recyclerView
    private var _isGridStyle: MutableLiveData<Boolean> = MutableLiveData(false)
    val isGridStyle: LiveData<Boolean> get() = _isGridStyle

    /**Flip recyclerView style state*/
    fun flipStyleState(){
        _isGridStyle.value?.let {
            _isGridStyle.value = !it
        }
    }

    fun flipDishFavouriteState(dish: Dish): Dish {
        dish.isFavoriteDish = !dish.isFavoriteDish
        return dish
    }



    fun deleteDishData(dish: Dish) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteDish(dish = dish)
    }

    fun updateDishModel(dish: Dish) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateDish(dish = dish)
    }

    fun insert(dish: Dish) = viewModelScope.launch {
        repository.insertDish(dish = dish)
    }


}