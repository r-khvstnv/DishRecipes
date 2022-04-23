package com.rkhvstnv.dishrecipes.di



import com.rkhvstnv.dishrecipes.di.data.DataModule
import com.rkhvstnv.dishrecipes.di.network.RandomDishModule
import com.rkhvstnv.dishrecipes.di.viewmodel.ViewModelBuilderModule
import com.rkhvstnv.dishrecipes.di.viewmodel.ViewModelModule
import dagger.Module


@Module(includes = [
    ViewModelModule::class,
    ViewModelBuilderModule::class,
    RandomDishModule::class,
    DataModule::class])
object AppModule {
}