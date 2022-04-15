package com.rkhvstnv.dishrecipes.model.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.rkhvstnv.dishrecipes.model.entities.Dish
import kotlinx.coroutines.flow.Flow

class DishRepository(private val dishDao: DishDao) {
    val allDishesList: Flow<List<Dish>> = dishDao.getAllDishList()
    val allFavDishesList: Flow<List<Dish>> = dishDao.getAllFavDishesList()

    fun getDishById(id: Int): Flow<Dish> = dishDao.getDishDetailsById(id)

    fun getDishesListByType(type: String): Flow<List<Dish>> =
        dishDao.queryDishesListByType(type)

    fun getDishesListByCategory(category: String): Flow<List<Dish>> =
        dishDao.queryDishesListByCategory(category)

    fun deleteDish(dish: Dish) = dishDao.deleteDishDetails(dish = dish)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertDish(dish: Dish){
        dishDao.insertDishDetails(dish)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateDish(dish: Dish){
        dishDao.updateDishDetails(dish)
    }
}