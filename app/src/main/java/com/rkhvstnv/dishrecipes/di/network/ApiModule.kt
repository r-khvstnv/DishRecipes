package com.rkhvstnv.dishrecipes.di.network

import com.rkhvstnv.dishrecipes.network.RandomDishApi
import com.rkhvstnv.dishrecipes.network.RandomDishService
import com.rkhvstnv.dishrecipes.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    @Provides
    fun provideRandomDishApi(): RandomDishApi{
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(RandomDishApi::class.java)
    }

    @Provides
    fun providesRandomDishService(api: RandomDishApi): RandomDishService{
        return RandomDishService(api = api)
    }
}