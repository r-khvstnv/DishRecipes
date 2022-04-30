package com.rkhvstnv.dishrecipes.random.di

import com.rkhvstnv.dishrecipes.random.RandomFragment
import dagger.Subcomponent

@Subcomponent(modules = [RandomModule::class])
interface RandomComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(): RandomComponent
    }
    fun inject(randomFragment: RandomFragment)
}