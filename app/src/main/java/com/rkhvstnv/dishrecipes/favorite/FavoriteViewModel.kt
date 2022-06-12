package com.rkhvstnv.dishrecipes.favorite

import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.app.db.DishRepository
import com.rkhvstnv.dishrecipes.app.models.Dish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(
    private val repository: DishRepository
    ): ViewModel() {

    val allFavDishesList: LiveData<List<Dish>> = repository.allFavDishesList.asLiveData()
    //style for recyclerView
    private var _isGridStyle: MutableLiveData<Boolean> = MutableLiveData(false)
    val isGridStyle: LiveData<Boolean> get() = _isGridStyle
    /**Flip recyclerView style state*/
    fun flipStyleState(){
        _isGridStyle.value?.let {
            _isGridStyle.value = !it
        }
    }
    fun flipDishFavoriteState(dish: Dish): Dish {
        dish.isFavoriteDish = !dish.isFavoriteDish
        return dish
    }
    fun updateDishData(dish: Dish) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateDish(dish = dish)
    }
    fun deleteDishData(dish: Dish) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteDish(dish = dish)
    }
}