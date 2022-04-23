package com.rkhvstnv.dishrecipes.di.viewmodel

import androidx.lifecycle.ViewModel
import com.rkhvstnv.dishrecipes.di.viewmodel.ViewModelKey
import com.rkhvstnv.dishrecipes.ui.activities.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    /*@Binds
    @[IntoMap ViewModelKey(FavoriteViewModel::class)]
    fun provideFavoriteViewModel(favoriteViewModel: FavoriteViewModel): ViewModel*/


    @Binds
    @[IntoMap ViewModelKey(MainViewModel::class)]
    abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel
}
