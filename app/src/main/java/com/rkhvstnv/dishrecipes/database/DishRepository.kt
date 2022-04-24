package com.rkhvstnv.dishrecipes.database

import androidx.annotation.WorkerThread
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.model.DishCategory
import com.rkhvstnv.dishrecipes.model.DishType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DishRepository @Inject constructor(private val dishDao: DishDao) {
    val allDishesList: Flow<List<Dish>> = dishDao.getAllDishList()
    val allFavDishesList: Flow<List<Dish>> = dishDao.getAllFavDishesList()

    val dishTypes: Flow<List<DishType>> = dishDao.getDishTypesList()
    val dishCategories: Flow<List<DishCategory>> = dishDao.getDishCategoriesList()


    fun getDishById(id: Int): Flow<Dish> = dishDao.getDishDetailsById(id)

    fun getDishesListByType(type: String): Flow<List<Dish>> =
        dishDao.queryDishesListByType(type)

    fun getDishesListByCategory(category: String): Flow<List<Dish>> =
        dishDao.queryDishesListByCategory(category)

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

    @WorkerThread
    suspend fun deleteDish(dish: Dish){
        dishDao.deleteDishDetails(mDish = dish)
    }
}