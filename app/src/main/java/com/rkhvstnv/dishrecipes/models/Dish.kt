package com.rkhvstnv.dishrecipes.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rkhvstnv.dishrecipes.utils.Constants


@Entity(tableName = Constants.TN_DISHES_TABLE)
data class Dish (
    @ColumnInfo(name = Constants.CI_IMAGE) val image: String = "",
    @ColumnInfo(name = Constants.CI_IMAGE_SOURCE) val imageSource: String = Constants.IMAGE_SOURCE_NETWORK,
    @ColumnInfo(name = Constants.CI_LABEL) val label: String,
    @ColumnInfo(name = Constants.CI_TYPE) val type: String,
    @ColumnInfo(name = Constants.CI_CATEGORY) val category: String,
    @ColumnInfo(name = Constants.CI_INGREDIENTS) val ingredients: String,
    @ColumnInfo(name = Constants.CI_COOKING_TIME) val cookingTime: Int,
    @ColumnInfo(name = Constants.CI_STEPS) val steps: String,
    @ColumnInfo(name = Constants.CI_IS_FAVORITE) var isFavoriteDish: Boolean = false,

    @PrimaryKey(autoGenerate = true) var id: Int = 0
)