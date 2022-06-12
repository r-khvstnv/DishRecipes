package com.rkhvstnv.dishrecipes.alldishes

import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.app.db.DishRepository
import com.rkhvstnv.dishrecipes.app.models.Dish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AllViewModel @Inject constructor(
    private val repository: DishRepository
    ): ViewModel() {

    private var _dishTypes = MutableLiveData<List<String>>()
    private var _dishCategories = MutableLiveData<List<String>>()
    val dishTypes: LiveData<List<String>> get() = _dishTypes
    val dishCategories: LiveData<List<String>> get() = _dishCategories
    val allDishesList: LiveData<List<Dish>> = repository.allDishesList.asLiveData()

    private var _dishListByType: MutableLiveData<List<Dish>> = MutableLiveData()
    private var _dishListByCategory: MutableLiveData<List<Dish>> = MutableLiveData()
    val dishListByType: LiveData<List<Dish>> get() = _dishListByType
    val dishListByCategory: LiveData<List<Dish>> get() = _dishListByCategory

    //style for recyclerView
    private var _isGridStyle: MutableLiveData<Boolean> = MutableLiveData(false)
    val isGridStyle: LiveData<Boolean> get() = _isGridStyle

    init {
        setTypesList()
        setCategoriesList()
    }

    fun getFilteredDishesListByType(params: String){
        _dishListByType =
            repository.getDishesListByType(params).asLiveData() as MutableLiveData<List<Dish>>
    }

    fun getFilteredDishesListByCategory(params: String){
        _dishListByCategory =
            repository.getDishesListByCategory(params).asLiveData() as MutableLiveData<List<Dish>>
    }

    /**Due to the inability to apply instant conversion to LiveData (asLiveData()),
     * method of receiving dishFilters implemented in this way*/
    private fun setTypesList(){
        val tmpTypesList = arrayListOf<String>()

        viewModelScope.launch(Dispatchers.IO){
            val typesList = repository.dishTypes

            typesList.collect {
                list ->
                list.let {
                    for (item in it){
                        tmpTypesList.add(item.name)
                    }
                }

                withContext(Dispatchers.Main){
                    _dishTypes.value = tmpTypesList.toList()
                    /*Due to parent list may changed, when user delete some dish.
                    We should manually reset current.
                    Otherwise when data is changed, updated list will be added to current*/
                    tmpTypesList.clear()
                }
            }
        }
    }
    private fun setCategoriesList(){
        val tmpCategoriesList = arrayListOf<String>()

        viewModelScope.launch(Dispatchers.IO){
            val categoriesList = repository.dishCategories
            categoriesList.collect {
                    list ->
                list.let {
                    for (item in it){
                        tmpCategoriesList.add(item.name)
                    }
                }

                withContext(Dispatchers.Main){
                    _dishCategories.value = tmpCategoriesList.toList()
                    /*Due to parent list may changed, when user delete some dish.
                    We should manually reset current.
                    Otherwise when data is changed, updated list will be added to current*/
                    tmpCategoriesList.clear()
                }
            }
        }
    }


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