package com.rkhvstnv.dishrecipes.di


import android.app.Application
import com.rkhvstnv.dishrecipes.network.RandomDishService
import com.rkhvstnv.dishrecipes.ui.activities.main.MainActivity
import com.rkhvstnv.dishrecipes.ui.fragments.addupdatedish.AddUpdateDishFragment
import com.rkhvstnv.dishrecipes.ui.fragments.alldishes.AllDishesFragment
import com.rkhvstnv.dishrecipes.ui.fragments.dishdetails.DishDetailsFragment
import com.rkhvstnv.dishrecipes.ui.fragments.favorite.FavoriteFragment
import com.rkhvstnv.dishrecipes.ui.fragments.randomdish.RandomDishFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun applicationContext(): Application

    fun inject(application: Application)
    fun inject(mainActivity: MainActivity)
    fun inject(service: RandomDishService)

    fun inject(fragment: AddUpdateDishFragment)
    fun inject(fragment: AllDishesFragment)
    fun inject(fragment: DishDetailsFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: RandomDishFragment)
}