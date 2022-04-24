package com.rkhvstnv.dishrecipes.utils

import android.content.Context
import com.rkhvstnv.dishrecipes.DishApplication
import com.rkhvstnv.dishrecipes.di.AppComponent

val Context.appComponent: AppComponent
    get() = when(this){
        is DishApplication -> appComponent
        else -> this.applicationContext.appComponent
    }

