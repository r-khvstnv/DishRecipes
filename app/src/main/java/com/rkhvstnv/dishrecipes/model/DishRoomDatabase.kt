package com.rkhvstnv.dishrecipes.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Dish::class], version = 1)
abstract class DishRoomDatabase : RoomDatabase(){

    abstract fun dishDao(): DishDao

    companion object{
        @Volatile
        private var INSTANCE: DishRoomDatabase? = null

        fun getDatabase(context: Context): DishRoomDatabase{
            // if the INSTANCE IS NOT NULL, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this){
                val  instance = Room.databaseBuilder(
                    context.applicationContext,
                    DishRoomDatabase::class.java,
                    "dish_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}