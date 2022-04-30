package com.rkhvstnv.dishrecipes.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rkhvstnv.dishrecipes.addupdate.AddUpdateViewModel
import com.rkhvstnv.dishrecipes.alldishes.AllViewModel
import com.rkhvstnv.dishrecipes.favorite.FavoriteViewModel
import com.rkhvstnv.dishrecipes.random.RandomViewModel

/*Factory is using only to trace difference in behaviour, when dagger2 is implemented*/
@Suppress("UNCHECKED_CAST")
class OldViewModelFactory constructor(val viewModel: ViewModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass){
            AddUpdateViewModel::class.java -> viewModel as T
            AllViewModel::class.java -> viewModel as T
            FavoriteViewModel::class.java -> viewModel as T
            RandomViewModel::class.java -> viewModel as T
            else -> throw IllegalStateException("Unknown Entity")
        }
    }
}
