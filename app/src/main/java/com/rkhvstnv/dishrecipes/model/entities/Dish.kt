package com.rkhvstnv.dishrecipes.model.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rkhvstnv.dishrecipes.utils.Constants
import kotlinx.parcelize.Parcelize


@Entity(tableName = "dishes_table")
data class Dish (
    @ColumnInfo val image: String,
    @ColumnInfo val imageSource: String = Constants.IMAGE_SOURCE_NETWORK,
    @ColumnInfo val label: String,
    @ColumnInfo val type: String,
    @ColumnInfo val category: String,
    @ColumnInfo val ingredients: String,
    @ColumnInfo val cookingTime: Int,
    @ColumnInfo val steps: String,
    @ColumnInfo var isFavoriteDish: Boolean = false,


    @PrimaryKey(autoGenerate = true) var id: Int = 0
)