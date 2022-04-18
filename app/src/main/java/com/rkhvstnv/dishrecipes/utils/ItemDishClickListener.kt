package com.rkhvstnv.dishrecipes.utils

import com.rkhvstnv.dishrecipes.models.Dish

interface ItemDishClickListener {
    fun onViewClick(itemId: Int)
    fun onFavoriteStateClick(dish: Dish)
    fun onEditClick(itemId: Int)
    fun onDeleteClick(dish: Dish)
    fun showOwnerError()
}