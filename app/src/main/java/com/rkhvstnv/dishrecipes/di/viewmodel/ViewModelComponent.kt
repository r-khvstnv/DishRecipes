package com.rkhvstnv.dishrecipes.di.viewmodel

import com.rkhvstnv.dishrecipes.ui.activities.main.MainActivity
import dagger.Subcomponent


@Subcomponent(modules = [ViewModelModule::class, ViewModelBuilderModule::class])
interface ViewModelComponent {


    @Subcomponent.Factory
    interface Factory{
        fun create(): ViewModelComponent
    }

    fun inject(mainActivity: MainActivity)
}




