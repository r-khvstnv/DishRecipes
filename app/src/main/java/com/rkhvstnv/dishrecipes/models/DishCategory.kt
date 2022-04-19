package com.rkhvstnv.dishrecipes.models

import androidx.room.ColumnInfo
import com.rkhvstnv.dishrecipes.utils.Constants

data class DishCategory(
    @ColumnInfo(name = Constants.CI_CATEGORY) val name: String
)
