package com.rkhvstnv.dishrecipes.ui.fragments.alldishes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rkhvstnv.dishrecipes.model.DishRepository

class AllDishViewModelFactory(private val repository: DishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AllDishesViewModel(repository = repository) as T
    }

}

class AllDishesViewModel(private val repository: DishRepository) : ViewModel() {

}