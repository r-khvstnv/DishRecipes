package com.rkhvstnv.dishrecipes.ui.fragments.dishdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.rkhvstnv.dishrecipes.base.BaseViewModel
import com.rkhvstnv.dishrecipes.database.DishRepository
import com.rkhvstnv.dishrecipes.model.Dish
import javax.inject.Inject

class DishDetailsViewModel @Inject constructor(private val repository: DishRepository): BaseViewModel(repository = repository) {
    var dish: LiveData<Dish>? = null

    fun setDish(id: Int){
        dish = repository.getDishById(id = id).asLiveData()
    }

}