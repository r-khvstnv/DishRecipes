package com.rkhvstnv.dishrecipes.favorite.di

import androidx.lifecycle.ViewModel
import com.rkhvstnv.dishrecipes.utils.ViewModelKey
import com.rkhvstnv.dishrecipes.favorite.FavoriteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FavoriteModule {
    @Binds
    @[IntoMap ViewModelKey(FavoriteViewModel::class)]
    abstract fun bindsViewModel(favoriteViewModel: FavoriteViewModel): ViewModel
}