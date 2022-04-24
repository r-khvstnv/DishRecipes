package com.rkhvstnv.dishrecipes

import android.app.Application
import com.rkhvstnv.dishrecipes.di.AppComponent
import com.rkhvstnv.dishrecipes.di.DaggerAppComponent
import com.rkhvstnv.dishrecipes.di.data.DataModule

open class DishApplication: Application() {

    lateinit var appComponent: AppComponent private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().dataModule(DataModule(this)).build()
        appComponent.inject(this)
    }
}