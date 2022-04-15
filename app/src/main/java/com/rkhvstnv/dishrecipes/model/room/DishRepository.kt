package com.rkhvstnv.dishrecipes.model.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.rkhvstnv.dishrecipes.model.Dish

class DishRepository(private val dishDao: DishDao) {
    val allDishesList: LiveData<List<Dish>> = dishDao.getAllDishList()
    val allFavDishesList: LiveData<List<Dish>> = dishDao.getAllFavDishesList()

    fun getDishById(id: Int): LiveData<Dish> = dishDao.getDishById(id)

    fun getDishesListByType(type: String): LiveData<List<Dish>> =
        dishDao.queryDishesListByType(type)

    fun getDishesListByCategory(category: String): LiveData<List<Dish>> =
        dishDao.queryDishesListByCategory(category)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertDishData(dish: Dish){
        dishDao.insertDishDetails(dish)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateDishData(dish: Dish){
        dishDao.updateDish(dish)
    }
}