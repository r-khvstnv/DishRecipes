package com.rkhvstnv.dishrecipes.ui.fragments.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.rkhvstnv.dishrecipes.base.BaseViewModel
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.database.DishRepository

class FavoriteViewModelFactory(private val repository: DishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return FavoriteViewModel(repository = repository) as T
    }
}
class FavoriteViewModel(private val repository: DishRepository):
    BaseViewModel(repository = repository) {

    val allFavDishesList: LiveData<List<Dish>> = repository.allFavDishesList.asLiveData()
}