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
    //style for recyclerView
    var isGridStyle: MutableLiveData<Boolean> = MutableLiveData(false)

    /**Flip recyclerView style state*/
    fun flipStyleState(){
        isGridStyle.value?.let {
            isGridStyle.value = !it
        }
    }

    /**Flip favorite state of Dish and Send updated data to Room DB*/
    fun flipAndUpdateDishFavouriteState(dish: Dish) = viewModelScope.launch(Dispatchers.IO) {
        dish.isFavoriteDish = !dish.isFavoriteDish
        repository.updateDishData(dish = dish)
    }
}