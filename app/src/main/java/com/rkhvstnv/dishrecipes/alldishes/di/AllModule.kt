package com.rkhvstnv.dishrecipes.alldishes.di

import androidx.lifecycle.ViewModel
import com.rkhvstnv.dishrecipes.utils.ViewModelKey
import com.rkhvstnv.dishrecipes.alldishes.AllViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AllModule {
    @Binds
    @[IntoMap ViewModelKey(AllViewModel::class)]
    abstract fun bindsViewModel(allViewModel: AllViewModel): ViewModel
}