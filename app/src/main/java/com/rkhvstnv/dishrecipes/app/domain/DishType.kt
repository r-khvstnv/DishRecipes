package com.rkhvstnv.dishrecipes.app.domain

import androidx.room.ColumnInfo
import com.rkhvstnv.dishrecipes.utils.Constants

/*Model is needed to get column from db*/
data class DishType(
    @ColumnInfo(name = Constants.CI_TYPE) val name: String
)