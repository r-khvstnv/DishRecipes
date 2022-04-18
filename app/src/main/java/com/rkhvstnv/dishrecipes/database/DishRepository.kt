package com.rkhvstnv.dishrecipes.database

import androidx.annotation.WorkerThread
import com.rkhvstnv.dishrecipes.models.Dish
import com.rkhvstnv.dishrecipes.models.DishFilters
import kotlinx.coroutines.flow.Flow

class DishRepository(private val dishDao: DishDao) {
    val allDishesList: Flow<List<Dish>> = dishDao.getAllDishList()
    val allFavDishesList: Flow<List<Dish>> = dishDao.getAllFavDishesList()
    val dishFilters: Flow<List<DishFilters>> = dishDao.getDishFiltersList()


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