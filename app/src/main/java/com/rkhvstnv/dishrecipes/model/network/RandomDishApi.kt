package com.rkhvstnv.dishrecipes.model.network

import com.rkhvstnv.dishrecipes.model.entities.RandomDish
import com.rkhvstnv.dishrecipes.utils.Constants
import com.rkhvstnv.dishrecipes.utils.Keys
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomDishApi {
    @GET(Constants.API_ENDPOINT)
    fun getRandomDish(
        @Query(Constants.API_KEY) apiKey: String,
        @Query(Constants.NUMBER) number: Int
    ): Single<RandomDish.Recipes>
}