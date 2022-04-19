package com.rkhvstnv.dishrecipes.ui.fragments.alldishes

import android.util.Log
import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.ui.fragments.bases.BaseViewModel
import com.rkhvstnv.dishrecipes.models.Dish
import com.rkhvstnv.dishrecipes.database.DishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllDishViewModelFactory(private val repository: DishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AllDishesViewModel(repository = repository) as T
    }
}

class AllDishesViewModel(private val repository: DishRepository):
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
                    Log.i("Test_Types", dishTypes.value.toString())
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
                    Log.i("Test_Categories", dishCategories.value.toString())
                }
            }
        }
    }
}