package com.rkhvstnv.dishrecipes.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rkhvstnv.dishrecipes.ui.fragments.addupdatedish.AddUpdateDishViewModel
import com.rkhvstnv.dishrecipes.ui.fragments.alldishes.AllDishesViewModel
import com.rkhvstnv.dishrecipes.ui.fragments.favorite.FavoriteViewModel
import com.rkhvstnv.dishrecipes.ui.fragments.randomdish.RandomDishViewModel


@Suppress("UNCHECKED_CAST")
class OldViewModelFactory constructor(val viewModel: ViewModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass){
            AddUpdateDishViewModel::class.java -> viewModel as T
            AllDishesViewModel::class.java -> viewModel as T
            FavoriteViewModel::class.java -> viewModel as T
            RandomDishViewModel::class.java -> viewModel as T
            else -> throw IllegalStateException("Unknown Entity")
        }
    }
}
