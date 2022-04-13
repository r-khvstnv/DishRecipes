package com.rkhvstnv.dishrecipes.ui.fragments.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rkhvstnv.dishrecipes.bases.BaseViewModel
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.model.DishRepository

class FavoriteViewModelFactory(private val repository: DishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return FavoriteViewModel(repository = repository) as T
    }
}


class FavoriteViewModel(repository: DishRepository) : BaseViewModel(repository = repository) {
    val allFavDishesList: LiveData<List<Dish>> = repository.allFavDishesList
}