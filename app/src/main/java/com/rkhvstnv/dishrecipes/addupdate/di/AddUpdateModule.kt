package com.rkhvstnv.dishrecipes.addupdate.di

import androidx.lifecycle.ViewModel
import com.rkhvstnv.dishrecipes.utils.ViewModelKey
import com.rkhvstnv.dishrecipes.addupdate.AddUpdateViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddUpdateModule {
    @Binds
    @[IntoMap ViewModelKey(AddUpdateViewModel::class)]
    abstract fun bindsViewModel(addUpdateViewModel: AddUpdateViewModel): ViewModel
}