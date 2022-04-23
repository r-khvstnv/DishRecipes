package com.rkhvstnv.dishrecipes.di.data

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.rkhvstnv.dishrecipes.DishApplication
import com.rkhvstnv.dishrecipes.database.DishDao
import com.rkhvstnv.dishrecipes.database.DishRepository
import com.rkhvstnv.dishrecipes.database.DishRoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
class DataModule(private val application: Application) {
    @Provides
    fun providesDishDatabase(application: Application): DishRoomDatabase{
        return DishRoomDatabase.getDatabase(application)
    }

    @Provides
    fun provideApplication(): Application{
        return application
    }

    @Provides
    fun providesDishDao(dishRoomDatabase: DishRoomDatabase): DishDao{
        return  dishRoomDatabase.dishDao()
    }

    @Provides
    fun provideDishRepo(dishDao: DishDao): DishRepository{
        return DishRepository(dishDao = dishDao)
    }


}

/*
@Module
abstract class DataBindModule{

    @Binds
    abstract fun bindDishDao(): DishDao
}*/
