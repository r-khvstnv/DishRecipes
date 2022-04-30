package com.rkhvstnv.dishrecipes.app.di

import com.rkhvstnv.dishrecipes.addupdate.di.AddUpdateComponent
import com.rkhvstnv.dishrecipes.alldishes.di.AllComponent
import com.rkhvstnv.dishrecipes.dishdetails.di.DetailsComponent
import com.rkhvstnv.dishrecipes.favorite.di.FavoriteComponent
import com.rkhvstnv.dishrecipes.random.di.RandomComponent
import dagger.Module


@Module( subcomponents = [
    AddUpdateComponent::class,
    AllComponent::class,
    DetailsComponent::class,
    FavoriteComponent::class,
    RandomComponent::class
])
object SubcomponentsModule