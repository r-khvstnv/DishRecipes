package com.rkhvstnv.dishrecipes.model

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class DishRepository(private val dishDao: DishDao) {
    //todo val allDishes: Flow<List<Dish>> = dishDao

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertDihData(dish: Dish){
        dishDao.insertDishDetails(dish = dish)
    }
}