package com.rkhvstnv.dishrecipes.app

import android.app.Application
import com.rkhvstnv.dishrecipes.app.di.AppComponent
import com.rkhvstnv.dishrecipes.app.di.DaggerAppComponent
import com.rkhvstnv.dishrecipes.app.di.DataModule

open class DishApplication: Application() {

    lateinit var appComponent: AppComponent private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().dataModule(DataModule(this)).build()
        appComponent.inject(this)
    }
}