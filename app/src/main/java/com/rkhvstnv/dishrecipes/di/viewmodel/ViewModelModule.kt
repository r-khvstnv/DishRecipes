package com.rkhvstnv.dishrecipes.di.viewmodel

import androidx.lifecycle.ViewModel
import com.rkhvstnv.dishrecipes.di.viewmodel.ViewModelKey
import com.rkhvstnv.dishrecipes.ui.activities.main.MainViewModel
import com.rkhvstnv.dishrecipes.ui.fragments.addupdatedish.AddUpdateDishViewModel
import com.rkhvstnv.dishrecipes.ui.fragments.alldishes.AllDishesViewModel
import com.rkhvstnv.dishrecipes.ui.fragments.dishdetails.DishDetailsViewModel
import com.rkhvstnv.dishrecipes.ui.fragments.favorite.FavoriteViewModel
import com.rkhvstnv.dishrecipes.ui.fragments.randomdish.RandomDishViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(MainViewModel::class)]
    abstract fun provideMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(AddUpdateDishViewModel::class)]
    abstract fun provideAddUpdateDishViewModel(viewModel: AddUpdateDishViewModel): ViewModel
    @Binds
    @[IntoMap ViewModelKey(AllDishesViewModel::class)]
    abstract fun provideAllDishesViewModel(viewModel: AllDishesViewModel): ViewModel
    @Binds
    @[IntoMap ViewModelKey(DishDetailsViewModel::class)]
    abstract fun provideDishDetailsViewModel(viewModel: DishDetailsViewModel): ViewModel
    @Binds
    @[IntoMap ViewModelKey(FavoriteViewModel::class)]
    abstract fun provideFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel
    @Binds
    @[IntoMap ViewModelKey(RandomDishViewModel::class)]
    abstract fun provideRandomDishViewModel(viewModel: RandomDishViewModel): ViewModel
}
