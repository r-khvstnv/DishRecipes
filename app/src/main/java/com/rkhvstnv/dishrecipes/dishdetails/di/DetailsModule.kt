package com.rkhvstnv.dishrecipes.dishdetails.di

import androidx.lifecycle.ViewModel
import com.rkhvstnv.dishrecipes.utils.ViewModelKey
import com.rkhvstnv.dishrecipes.dishdetails.DetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class DetailsModule {
    @Binds
    @[IntoMap ViewModelKey(DetailsViewModel::class)]
    abstract fun bindsViewModel(detailsViewModel: DetailsViewModel): ViewModel
}