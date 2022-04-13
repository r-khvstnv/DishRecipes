package com.rkhvstnv.dishrecipes.model

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class DishRepository(private val dishDao: DishDao) {
    val allDishesList: LiveData<List<Dish>> = dishDao.getAllDishList()
    val allFavDishesList: LiveData<List<Dish>> = dishDao.getAllFavDishesList()

    fun getDishById(id: Int): LiveData<Dish>{
        return dishDao.getDishById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertDishData(dish: Dish){
        dishDao.insertDishDetails(dish = dish)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateDishData(dish: Dish){
        dishDao.updateDish(dish = dish)
    }
}