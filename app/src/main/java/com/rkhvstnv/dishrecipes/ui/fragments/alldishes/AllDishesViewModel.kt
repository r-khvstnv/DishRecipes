package com.rkhvstnv.dishrecipes.ui.fragments.alldishes

import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.bases.BaseViewModel
import com.rkhvstnv.dishrecipes.model.entities.Dish
import com.rkhvstnv.dishrecipes.model.room.DishRepository

class AllDishViewModelFactory(private val repository: DishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AllDishesViewModel(repository = repository) as T
    }
}

class AllDishesViewModel(repository: DishRepository) : BaseViewModel(repository = repository) {
    val allDishesList: LiveData<List<Dish>> = repository.allDishesList.asLiveData()

    fun getFilteredDishesListByType(params: String): LiveData<List<Dish>>{
        return repository.getDishesListByType(params).asLiveData()
    }
    fun getFilteredDishesListByCategory(params: String): LiveData<List<Dish>>{
        return repository.getDishesListByCategory(params).asLiveData()
    }
}