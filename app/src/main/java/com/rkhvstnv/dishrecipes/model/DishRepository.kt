package com.rkhvstnv.dishrecipes.model

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class DishRepository(private val dishDao: DishDao) {
    val allDishesList: Flow<List<Dish>> = dishDao.getAllDishList()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertDishData(dish: Dish){
        dishDao.insertDishDetails(dish = dish)
    }
}