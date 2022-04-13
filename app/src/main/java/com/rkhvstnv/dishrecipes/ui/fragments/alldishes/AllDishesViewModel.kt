package com.rkhvstnv.dishrecipes.ui.fragments.alldishes

import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.bases.BaseViewModel
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.model.DishRepository

class AllDishViewModelFactory(private val repository: DishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AllDishesViewModel(repository = repository) as T
    }
}

class AllDishesViewModel(repository: DishRepository) : BaseViewModel(repository = repository) {
    val allDishesList: LiveData<List<Dish>> = repository.allDishesList

    fun getFilteredDishesListByType(params: String) = repository.getDishesListByType(params)
    fun getFilteredDishesListByCategory(params: String) = repository.getDishesListByCategory(params)
}