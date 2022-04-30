package com.rkhvstnv.dishrecipes.app.di

import android.app.Application
import com.rkhvstnv.dishrecipes.app.data.DishDao
import com.rkhvstnv.dishrecipes.app.data.DishRepository
import com.rkhvstnv.dishrecipes.app.data.DishRoomDatabase
import dagger.Module
import dagger.Provides


@Module
class DataModule(private val application: Application) {
    @Provides
    fun providesDishDatabase(application: Application): DishRoomDatabase {
        return DishRoomDatabase.getDatabase(application)
    }

    @Provides
    fun provideApplication(): Application{
        return application
    }

    @Provides
    fun providesDishDao(dishRoomDatabase: DishRoomDatabase): DishDao {
        return  dishRoomDatabase.dishDao()
    }

    @Provides
    fun provideDishRepo(dishDao: DishDao): DishRepository {
        return DishRepository(dishDao = dishDao)
    }
}
