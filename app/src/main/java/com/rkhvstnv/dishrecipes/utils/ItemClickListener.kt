package com.rkhvstnv.dishrecipes.utils

import com.rkhvstnv.dishrecipes.model.Dish

interface ItemClickListener {
    fun onItemClick(itemId: Int)
    fun onItemFavoriteStateClick(dish: Dish)
}