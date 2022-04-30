package com.rkhvstnv.dishrecipes.dishdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.rkhvstnv.dishrecipes.app.presenter.BaseViewModel
import com.rkhvstnv.dishrecipes.app.data.DishRepository
import com.rkhvstnv.dishrecipes.app.domain.Dish
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: DishRepository
    ): BaseViewModel(repository = repository) {

    var dish: LiveData<Dish>? = null

    fun setDish(id: Int){
        dish = repository.getDishById(id = id).asLiveData()
    }
}