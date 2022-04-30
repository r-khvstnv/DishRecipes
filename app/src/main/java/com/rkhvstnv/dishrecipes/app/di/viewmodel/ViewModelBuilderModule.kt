package com.rkhvstnv.dishrecipes.app.di.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.rkhvstnv.dishrecipes.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBuilderModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}