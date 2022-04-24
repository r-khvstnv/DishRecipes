package com.rkhvstnv.dishrecipes.ui.fragments.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.rkhvstnv.dishrecipes.base.BaseViewModel
import com.rkhvstnv.dishrecipes.database.DishRepository
import com.rkhvstnv.dishrecipes.model.Dish
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(
    private val repository: DishRepository
    ): BaseViewModel(repository = repository) {

    val allFavDishesList: LiveData<List<Dish>> = repository.allFavDishesList.asLiveData()
}