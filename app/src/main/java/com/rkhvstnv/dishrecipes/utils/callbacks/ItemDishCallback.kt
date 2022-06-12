package com.rkhvstnv.dishrecipes.utils.callbacks

import com.rkhvstnv.dishrecipes.app.models.Dish

/**Callbacks for dishes recyclerView*/
interface ItemDishCallback {
    fun onViewClick(itemId: Int)
    fun onFavoriteStateClick(dish: Dish)
    fun onEditClick(itemId: Int)
    fun onDeleteClick(dish: Dish)
    fun showOwnerError()
}