package com.rkhvstnv.dishrecipes.di

import android.content.Context
import com.rkhvstnv.dishrecipes.di.viewmodel.ViewModelComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance appContext: Context): AppComponent
    }

    fun viewModelComponent(): ViewModelComponent.Factory
}