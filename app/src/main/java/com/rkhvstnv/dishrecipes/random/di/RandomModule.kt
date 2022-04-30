package com.rkhvstnv.dishrecipes.random.di

import androidx.lifecycle.ViewModel
import com.rkhvstnv.dishrecipes.utils.ViewModelKey
import com.rkhvstnv.dishrecipes.random.RandomViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RandomModule {
    @Binds
    @[IntoMap ViewModelKey(RandomViewModel::class)]
    abstract fun bindsViewModel(randomViewModel: RandomViewModel): ViewModel
}