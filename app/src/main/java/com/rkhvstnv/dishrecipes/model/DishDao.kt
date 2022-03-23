package com.rkhvstnv.dishrecipes.model

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert


@Dao
interface DishDao {
    @Insert
    suspend fun insertDishDetails(dish: Dish)


}