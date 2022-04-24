package com.rkhvstnv.dishrecipes.di

import com.rkhvstnv.dishrecipes.di.data.DataModule
import com.rkhvstnv.dishrecipes.di.network.ApiModule
import com.rkhvstnv.dishrecipes.di.viewmodel.ViewModelBuilderModule
import com.rkhvstnv.dishrecipes.di.viewmodel.ViewModelModule
import dagger.Module


@Module(includes = [
    ViewModelModule::class,
    ViewModelBuilderModule::class,
    ApiModule::class,
    DataModule::class])
object AppModule