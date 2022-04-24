package com.rkhvstnv.dishrecipes.model

import androidx.room.ColumnInfo
import com.rkhvstnv.dishrecipes.utils.Constants

/*Model is needed to get column from db*/
data class DishCategory(
    @ColumnInfo(name = Constants.CI_CATEGORY) val name: String
)
