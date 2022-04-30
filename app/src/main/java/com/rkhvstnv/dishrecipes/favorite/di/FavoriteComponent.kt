package com.rkhvstnv.dishrecipes.favorite.di

import com.rkhvstnv.dishrecipes.favorite.FavoriteFragment
import dagger.Subcomponent


@Subcomponent(modules = [FavoriteModule::class])
interface FavoriteComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): FavoriteComponent
    }

    fun inject(favoriteFragment: FavoriteFragment)
}