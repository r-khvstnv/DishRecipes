package com.rkhvstnv.dishrecipes.alldishes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rkhvstnv.dishrecipes.app.presenter.BaseViewModel
import com.rkhvstnv.dishrecipes.app.data.DishRepository
import com.rkhvstnv.dishrecipes.app.domain.Dish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AllViewModel @Inject constructor(
    private val repository: DishRepository
    ): BaseViewModel(repository = repository) {

    private var _dishTypes = MutableLiveData<List<String>>()
    private var _dishCategories = MutableLiveData<List<String>>()
    val dishTypes: LiveData<List<String>> get() = _dishTypes
    val dishCategories: LiveData<List<String>> get() = _dishCategories
    val allDishesList: LiveData<List<Dish>> = repository.allDishesList.asLiveData()

    private var _dishListByType: MutableLiveData<List<Dish>> = MutableLiveData()
    private var _dishListByCategory: MutableLiveData<List<Dish>> = MutableLiveData()
    val dishListByType: LiveData<List<Dish>> get() = _dishListByType
    val dishListByCategory: LiveData<List<Dish>> get() = _dishListByCategory

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
}