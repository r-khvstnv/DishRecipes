package com.rkhvstnv.dishrecipes.ui.fragments.alldishes

import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.base.BaseViewModel
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.database.DishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AllDishViewModelFactory(private val repository: DishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AllDishesViewModel(repository = repository) as T
    }
}

class TestFactory constructor(val viewModel: AllDishesViewModel): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass == AllDishesViewModel::class.java){
            @Suppress("UNCHECKED_CAST")
            viewModel as T
        } else{
            throw IllegalStateException("Unknown Entity")
        }
}

class AllDishesViewModel @Inject constructor(private val repository: DishRepository):
    BaseViewModel(repository = repository) {

    private var _dishTypes = MutableLiveData<List<String>>()
    private var _dishCategories = MutableLiveData<List<String>>()
    val dishTypes: LiveData<List<String>> get() = _dishTypes
    val dishCategories: LiveData<List<String>> get() = _dishCategories
    val allDishesList: LiveData<List<Dish>> = repository.allDishesList.asLiveData()

    init {
        setTypesList()
        setCategoriesList()
    }

    fun getFilteredDishesListByType(params: String): LiveData<List<Dish>>{
        return repository.getDishesListByType(params).asLiveData()
    }

    fun getFilteredDishesListByCategory(params: String): LiveData<List<Dish>>{
        return repository.getDishesListByCategory(params).asLiveData()
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
                }
            }
        }
    }
}