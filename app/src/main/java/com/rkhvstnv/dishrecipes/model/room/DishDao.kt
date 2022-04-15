package com.rkhvstnv.dishrecipes.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rkhvstnv.dishrecipes.model.entities.Dish
import kotlinx.coroutines.flow.Flow


@Dao
interface DishDao {
    @Insert
    suspend fun insertDishDetails(mDish: Dish)

    @Query("SELECT * FROM DISHES_TABLE ORDER BY ID")
    fun getAllDishList(): Flow<List<Dish>>

    @Query("SELECT * FROM DISHES_TABLE WHERE ID = :dishId")
    fun getDishDetailsById(dishId: Int): Flow<Dish>

    @Update
    fun updateDishDetails(mDish: Dish)

    @Query("SELECT * FROM DISHES_TABLE WHERE isFavoriteDish = 1")
    fun getAllFavDishesList(): Flow<List<Dish>>

    @Query("SELECT * FROM DISHES_TABLE WHERE type = :mType")
    fun queryDishesListByType(mType: String): Flow<List<Dish>>

    @Query("SELECT * FROM DISHES_TABLE WHERE category = :mCategory")
    fun queryDishesListByCategory(mCategory: String): Flow<List<Dish>>

    @Delete
    fun deleteDishDetails(dish: Dish)
}