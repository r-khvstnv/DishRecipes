package com.rkhvstnv.dishrecipes.utils.callbacks

/**Callback for items in type/category recyclerView
 * filterType = type/category
 * params = subtype
 * */
interface ItemFilterClickListener {
    fun onClick(filterType: String, params: String)
}