package com.rkhvstnv.dishrecipes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes_table")
data class Dish (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo val image: String,
    @ColumnInfo val imageSource: String,
    @ColumnInfo val label: String,
    @ColumnInfo val type: String,
    @ColumnInfo val category: String,
    @ColumnInfo val ingredients: String,
    @ColumnInfo val cookingTime: Int,
    @ColumnInfo val steps: String,
    @ColumnInfo val isFavoriteDish: Boolean = false
)