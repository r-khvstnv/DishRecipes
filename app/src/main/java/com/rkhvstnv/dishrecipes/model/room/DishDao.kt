package com.rkhvstnv.dishrecipes.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rkhvstnv.dishrecipes.model.Dish


@Dao
interface DishDao {
    @Insert
    suspend fun insertDishDetails(mDish: Dish)

    @Query("SELECT * FROM DISHES_TABLE ORDER BY ID")
    fun getAllDishList(): LiveData<List<Dish>>

    @Query("SELECT * FROM DISHES_TABLE WHERE ID = :dishId")
    fun getDishById(dishId: Int): LiveData<Dish>

    @Update
    fun updateDish(mDish: Dish)

    @Query("SELECT * FROM DISHES_TABLE WHERE isFavoriteDish = 1")
    fun getAllFavDishesList(): LiveData<List<Dish>>

    @Query("SELECT * FROM DISHES_TABLE WHERE type = :mType")
    fun queryDishesListByType(mType: String): LiveData<List<Dish>>

    @Query("SELECT * FROM DISHES_TABLE WHERE category = :mCategory")
    fun queryDishesListByCategory(mCategory: String): LiveData<List<Dish>>
}