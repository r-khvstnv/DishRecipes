package com.rkhvstnv.dishrecipes.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface DishDao {
    @Insert
    suspend fun insertDishDetails(dish: Dish)

    @Query("SELECT * FROM DISHES_TABLE ORDER BY ID")
    fun getAllDishList(): Flow<List<Dish>>
}