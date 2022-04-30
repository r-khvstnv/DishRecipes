package com.rkhvstnv.dishrecipes.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.rkhvstnv.dishrecipes.app.presenter.BaseViewModel
import com.rkhvstnv.dishrecipes.app.data.DishRepository
import com.rkhvstnv.dishrecipes.app.domain.Dish
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(
    private val repository: DishRepository
    ): BaseViewModel(repository = repository) {

    val allFavDishesList: LiveData<List<Dish>> = repository.allFavDishesList.asLiveData()
}