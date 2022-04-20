package com.rkhvstnv.dishrecipes.database

import androidx.room.*
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.model.DishCategory
import com.rkhvstnv.dishrecipes.model.DishType
import com.rkhvstnv.dishrecipes.utils.Constants
import kotlinx.coroutines.flow.Flow


@Dao
interface DishDao {
    @Insert
    suspend fun insertDishDetails(mDish: Dish)

    @Query("SELECT * FROM ${Constants.TN_DISHES_TABLE} ORDER BY ${Constants.CI_ID}")
    fun getAllDishList(): Flow<List<Dish>>

    @Query("SELECT * FROM ${Constants.TN_DISHES_TABLE} WHERE ${Constants.CI_ID} = :dishId")
    fun getDishDetailsById(dishId: Int): Flow<Dish>

    @Update
    fun updateDishDetails(mDish: Dish)

    @Query("SELECT * FROM ${Constants.TN_DISHES_TABLE} WHERE ${Constants.CI_IS_FAVORITE} = 1")
    fun getAllFavDishesList(): Flow<List<Dish>>

    @Query("SELECT * FROM ${Constants.TN_DISHES_TABLE} WHERE ${Constants.CI_TYPE} = :mType")
    fun queryDishesListByType(mType: String): Flow<List<Dish>>

    @Query("SELECT * FROM ${Constants.TN_DISHES_TABLE} WHERE ${Constants.CI_CATEGORY} = :mCategory")
    fun queryDishesListByCategory(mCategory: String): Flow<List<Dish>>

    @Delete
    fun deleteDishDetails(dish: Dish)

    @Query("SELECT DISTINCT ${Constants.CI_TYPE} FROM ${Constants.TN_DISHES_TABLE}")
    fun getDishTypesList(): Flow<List<DishType>>

    @Query("SELECT DISTINCT ${Constants.CI_CATEGORY} FROM ${Constants.TN_DISHES_TABLE}")
    fun getDishCategoriesList(): Flow<List<DishCategory>>
}