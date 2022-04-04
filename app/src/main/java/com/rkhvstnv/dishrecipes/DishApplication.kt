package com.rkhvstnv.dishrecipes

import android.app.Application
import com.rkhvstnv.dishrecipes.model.DishRepository
import com.rkhvstnv.dishrecipes.model.DishRoomDatabase

class DishApplication: Application() {
    private val database by lazy {
        DishRoomDatabase.getDatabase(this@DishApplication)
    }
    val repository by lazy { DishRepository(database.dishDao()) }
}