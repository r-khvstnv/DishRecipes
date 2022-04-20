package com.rkhvstnv.dishrecipes.model

import androidx.room.ColumnInfo
import com.rkhvstnv.dishrecipes.utils.Constants

data class DishFilters(
    @ColumnInfo(name = Constants.CI_TYPE) val type: String,
    @ColumnInfo(name = Constants.CI_CATEGORY) val category: String
)
