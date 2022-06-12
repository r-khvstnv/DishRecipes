package com.rkhvstnv.dishrecipes.addupdate.di

import com.rkhvstnv.dishrecipes.addupdate.AddUpdateFragment
import dagger.Subcomponent


@Subcomponent(modules = [AddUpdateModule::class])
interface AddUpdateComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(): AddUpdateComponent
    }

    fun inject(addUpdateFragment: AddUpdateFragment)
}