package com.rkhvstnv.dishrecipes.utils

object Constants {

    //Internal image directory
    const val IMAGE_DIRECTORY: String = "DishImageDir"

    //Types of image source
    const val IMAGE_SOURCE_INTERNAL: String  = "image_internal"
    const val IMAGE_SOURCE_NETWORK: String  = "image_network"

    //Default value for navigation args
    const val DEF_ARGS_INT: Int = -5

    //Spoonacular API
    const val API_ENDPOINT: String  = "recipes/random"
    const val NUMBER: String  = "number"
    const val BASE_URL: String  = "https://api.spoonacular.com/"
    const val API_KEY: String  = "apiKey"
    const val NUMBER_VALUE: Int = 1

    //Room database
    const val TN_DISHES_TABLE = "dishes_table"
    const val CI_IMAGE: String  = "image"
    const val CI_IMAGE_SOURCE: String  = "imageSource"
    const val CI_LABEL: String  = "label"
    const val CI_TYPE: String  = "type"
    const val CI_CATEGORY: String  = "category"
    const val CI_INGREDIENTS: String  = "ingredients"
    const val CI_COOKING_TIME: String  = "cookingTime"
    const val CI_STEPS: String  = "steps"
    const val CI_IS_FAVORITE: String  = "isFavoriteDish"
    const val CI_ID: String = "id"
}