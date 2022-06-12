package com.rkhvstnv.dishrecipes.dishdetails.di

import com.rkhvstnv.dishrecipes.dishdetails.DetailsFragment
import dagger.Subcomponent

@Subcomponent(modules = [DetailsModule::class])
interface DetailsComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(): DetailsComponent
    }

    fun inject(detailsFragment: DetailsFragment)
}