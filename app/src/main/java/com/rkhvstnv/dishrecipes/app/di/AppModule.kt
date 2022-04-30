package com.rkhvstnv.dishrecipes.app.di

import com.rkhvstnv.dishrecipes.api.di.ApiModule
import com.rkhvstnv.dishrecipes.app.di.viewmodel.ViewModelBuilderModule
import com.rkhvstnv.dishrecipes.app.di.viewmodel.ViewModelModule
import dagger.Module


@Module(includes = [
    ViewModelModule::class,
    ViewModelBuilderModule::class,
    ApiModule::class,
    DataModule::class])
object AppModule