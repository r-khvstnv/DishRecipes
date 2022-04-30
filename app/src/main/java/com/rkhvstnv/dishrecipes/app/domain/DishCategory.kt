package com.rkhvstnv.dishrecipes.app.domain

import androidx.room.ColumnInfo
import com.rkhvstnv.dishrecipes.utils.Constants

/*Model is needed to get column from db*/
data class DishCategory(
    @ColumnInfo(name = Constants.CI_CATEGORY) val name: String
)
