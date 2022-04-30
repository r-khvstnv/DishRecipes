package com.rkhvstnv.dishrecipes.api

import com.rkhvstnv.dishrecipes.app.domain.RandomDish
import com.rkhvstnv.dishrecipes.utils.Constants
import com.rkhvstnv.dishrecipes.utils.Keys
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RandomDishService @Inject constructor(private val api: RandomDishApi) {

    fun getRandomDish(): Single<RandomDish.Recipes>{
        return api.getRandomDish(Keys.API_KEY_VALUE, Constants.NUMBER_VALUE)
    }
}