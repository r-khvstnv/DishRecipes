package com.rkhvstnv.dishrecipes.models

import androidx.room.ColumnInfo
import com.rkhvstnv.dishrecipes.utils.Constants

data class DishType(
    @ColumnInfo(name = Constants.CI_TYPE) val name: String
)