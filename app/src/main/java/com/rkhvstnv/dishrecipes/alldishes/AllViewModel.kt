package com.rkhvstnv.dishrecipes.alldishes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rkhvstnv.dishrecipes.app.data.DishRepository
import com.rkhvstnv.dishrecipes.app.domain.Dish
import com.rkhvstnv.dishrecipes.app.presenter.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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
    /**
     * By default  it contains all available dishes
     * */
    private var _selectedDishListList: MutableLiveData<List<Dish>> = MutableLiveData()
    val selectedDishListList: LiveData<List<Dish>> get() = _selectedDishListList
    private var _filterParam: MutableLiveData<String> = MutableLiveData("")
    val filterParam: LiveData<String> get() = _filterParam


    init {
        setTypesList()
        setCategoriesList()
        getAllDishes()
    }

    fun getFilteredDishesListByType(params: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.getDishesListByType(params).collectLatest {
                list ->
                _selectedDishListList.postValue(list)
            }
        }
        _filterParam.postValue(params)
    }

    fun getFilteredDishesListByCategory(params: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.getDishesListByCategory(params).collectLatest {
                    list ->
                _selectedDishListList.postValue(list)
            }
        }
        _filterParam.postValue(params)
    }

    fun getAllDishes(){
        viewModelScope.launch(Dispatchers.IO){
            repository.allDishesList.collectLatest {
                    list ->
                _selectedDishListList.postValue(list)
            }
        }
        _filterParam.postValue("")
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