package com.rkhvstnv.dishrecipes.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface DishDao {
    @Insert
    suspend fun insertDishDetails(dish: Dish)

    @Query("SELECT * FROM DISHES_TABLE ORDER BY ID")
    fun getAllDishList(): LiveData<List<Dish>>

    @Query("SELECT * FROM DISHES_TABLE WHERE ID = :dishId")
    fun getDishById(dishId: Int): LiveData<Dish>

    @Update
    fun updateDish(dish: Dish)
}