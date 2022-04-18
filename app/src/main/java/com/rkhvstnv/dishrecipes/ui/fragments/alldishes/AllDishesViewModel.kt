package com.rkhvstnv.dishrecipes.ui.fragments.alldishes

import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.ui.fragments.bases.BaseViewModel
import com.rkhvstnv.dishrecipes.models.Dish
import com.rkhvstnv.dishrecipes.database.DishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AllDishViewModelFactory(private val repository: DishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AllDishesViewModel(repository = repository) as T
    }
}

class AllDishesViewModel(private val repository: DishRepository):
    BaseViewModel(repository = repository) {

    private var _dishTypes = arrayListOf<String>()
    private var _dishCategories = arrayListOf<String>()

    val dishTypes get() = _dishTypes
    val dishCategories get() = _dishCategories
    val allDishesList: LiveData<List<Dish>> = repository.allDishesList.asLiveData()

    init {
        setFilterList()
    }

    fun getFilteredDishesListByType(params: String): LiveData<List<Dish>>{
        return repository.getDishesListByType(params).asLiveData()
    }

    fun getFilteredDishesListByCategory(params: String): LiveData<List<Dish>>{
        return repository.getDishesListByCategory(params).asLiveData()
    }

    /**Due to the inability to apply instant conversion to LiveData (asLiveData()),
     * method of receiving dishFilters implemented in this way*/
    private fun setFilterList(){
        viewModelScope.launch(Dispatchers.IO){
            val list = repository.dishFilters
            list.collect {
                    filters ->
                filters.let {
                    for (item in it){
                        if (!_dishTypes.contains(item.type)){
                            _dishTypes.add(item.type)
                        }

                        if (!_dishCategories.contains(item.category)){
                            _dishCategories.add(item.category)
                        }
                    }
                }
            }
        }
    }
}