package com.rkhvstnv.dishrecipes.ui.fragments.addupdatedish

import android.graphics.Bitmap
import android.text.TextUtils
import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.model.DishRepository
import kotlinx.coroutines.launch

class AddUpdateDishViewModelFactory(private val repository: DishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AddUpdateDishViewModel(repository = repository) as T
    }

}

class AddUpdateDishViewModel(private val repository: DishRepository) : ViewModel() {
    var dishBitmap: Bitmap? = null
    var imagePath: String = ""

    fun insert(dish: Dish) = viewModelScope.launch {
        repository.insertDishData(dish = dish)
    }

}