package com.rkhvstnv.dishrecipes.api

import com.rkhvstnv.dishrecipes.app.models.RandomDish
import com.rkhvstnv.dishrecipes.utils.Constants
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