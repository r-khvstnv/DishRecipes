package com.rkhvstnv.dishrecipes.utils

import com.rkhvstnv.dishrecipes.model.Dish

interface ItemDishClickListener {
    fun onItemClick(itemId: Int)
    fun onItemFavoriteStateClick(dish: Dish)
}