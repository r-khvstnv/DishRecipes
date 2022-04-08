package com.rkhvstnv.dishrecipes.model

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class DishRepository(private val dishDao: DishDao) {
    val allDishesList: LiveData<List<Dish>> = dishDao.getAllDishList()

    fun getDishById(id: Int): LiveData<Dish>{
        return dishDao.getDishById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertDishData(dish: Dish){
        dishDao.insertDishDetails(dish = dish)
    }
}