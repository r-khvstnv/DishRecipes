package com.rkhvstnv.dishrecipes.alldishes.di

import com.rkhvstnv.dishrecipes.alldishes.AllFragment
import dagger.Subcomponent

@Subcomponent(modules = [AllModule::class])
interface AllComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(): AllComponent
    }

    fun insert(allFragment: AllFragment)
}