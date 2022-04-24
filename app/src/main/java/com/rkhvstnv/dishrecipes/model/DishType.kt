package com.rkhvstnv.dishrecipes.model

import androidx.room.ColumnInfo
import com.rkhvstnv.dishrecipes.utils.Constants

/*Model is needed to get column from db*/
data class DishType(
    @ColumnInfo(name = Constants.CI_TYPE) val name: String
)