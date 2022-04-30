package com.rkhvstnv.dishrecipes.app.di


import android.app.Application
import com.rkhvstnv.dishrecipes.api.RandomDishService
import com.rkhvstnv.dishrecipes.addupdate.di.AddUpdateComponent
import com.rkhvstnv.dishrecipes.alldishes.di.AllComponent
import com.rkhvstnv.dishrecipes.dishdetails.di.DetailsComponent
import com.rkhvstnv.dishrecipes.favorite.di.FavoriteComponent
import com.rkhvstnv.dishrecipes.random.di.RandomComponent
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, SubcomponentsModule::class])
interface AppComponent {

    fun applicationContext(): Application

    fun inject(application: Application)

    fun inject(service: RandomDishService)

    /*fun inject(fragment: AddUpdateFragment)
    fun inject(fragment: AllFragment)
    fun inject(fragment: DetailsFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: RandomFragment)*/

    fun addUpdateComponent(): AddUpdateComponent.Factory
    fun allComponent(): AllComponent.Factory
    fun detailsComponent(): DetailsComponent.Factory
    fun favoriteComponent(): FavoriteComponent.Factory
    fun randomComponent(): RandomComponent.Factory
}