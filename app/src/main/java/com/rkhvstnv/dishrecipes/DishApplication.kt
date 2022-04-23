package com.rkhvstnv.dishrecipes

import android.app.Application
import com.rkhvstnv.dishrecipes.database.DishRepository
import com.rkhvstnv.dishrecipes.database.DishRoomDatabase
import com.rkhvstnv.dishrecipes.di.AppComponent
import com.rkhvstnv.dishrecipes.di.DaggerAppComponent

//todo these
open class DishApplication: Application() {
    private val database by lazy {
        DishRoomDatabase.getDatabase(this@DishApplication)
    }
    val repository by lazy { DishRepository(database.dishDao()) }

    val appComponent: AppComponent by lazy{
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent{
        return DaggerAppComponent.factory().create(applicationContext)
    }
}