package com.rkhvstnv.dishrecipes.ui.fragments.alldishes

import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.model.DishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllDishViewModelFactory(private val repository: DishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AllDishesViewModel(repository = repository) as T
    }

}

class AllDishesViewModel(private val repository: DishRepository) : ViewModel() {
    val allDishesList: LiveData<List<Dish>> = repository.allDishesList
    var isGridStyle: MutableLiveData<Boolean> = MutableLiveData(false)

    fun flipStyleState(){
        isGridStyle.value?.let {
            isGridStyle.value = !it
        }
    }

    fun updateDishFavouriteState(dish: Dish) = viewModelScope.launch(Dispatchers.IO) {
        dish.isFavoriteDish = !dish.isFavoriteDish
        repository.updateDishData(dish = dish)
    }
}