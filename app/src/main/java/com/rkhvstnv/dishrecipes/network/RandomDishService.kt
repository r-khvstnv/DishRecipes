package com.rkhvstnv.dishrecipes.network

import com.rkhvstnv.dishrecipes.model.RandomDish
import com.rkhvstnv.dishrecipes.utils.Constants
import com.rkhvstnv.dishrecipes.utils.Keys
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RandomDishService {
    private val api = Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RandomDishApi::class.java)

    fun getRandomDish(): Single<RandomDish.Recipes>{
        return api.getRandomDish(Keys.API_KEY_VALUE, Constants.NUMBER_VALUE)
    }
}