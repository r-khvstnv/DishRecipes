package com.rkhvstnv.dishrecipes.random

import android.net.Uri
import android.os.Build
import android.text.Html
import android.util.Log
import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.app.presenter.BaseViewModel
import com.rkhvstnv.dishrecipes.app.domain.Dish
import com.rkhvstnv.dishrecipes.app.domain.RandomDish
import com.rkhvstnv.dishrecipes.api.RandomDishService
import com.rkhvstnv.dishrecipes.app.data.DishRepository
import com.rkhvstnv.dishrecipes.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RandomViewModel @Inject constructor(
    private val repository: DishRepository,
    private val randomDishService: RandomDishService
    ): BaseViewModel(repository = repository) {

    private val compositeDisposable = CompositeDisposable()

    private val _randomDishInLoading = MutableLiveData<Boolean>()
    private val _randomDishLoadingErrorOccurred = MutableLiveData<Boolean>()
    private val _dish = MutableLiveData<Dish>()
    //Link of recipe received from Api
    private val _dishSourceUri = MutableLiveData<Uri>()

    val randomDishInLoading: LiveData<Boolean> get() = _randomDishInLoading
    val randomDishLoadingErrorOccurred: LiveData<Boolean> get() = _randomDishLoadingErrorOccurred
    val dish: LiveData<Dish> get() = _dish
    val dishSourceUri get() = _dishSourceUri


    init {
        fetchRandomDish()
    }


    fun refreshRandomDish(){
        fetchRandomDish()
    }


    private fun fetchRandomDish(){
        _randomDishInLoading.value = true

        compositeDisposable.add(
            randomDishService.getRandomDish()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RandomDish.Recipes>(){
                    override fun onSuccess(t: RandomDish.Recipes) {
                        _randomDishInLoading.value = false
                        fetchDish(t)
                    }

                    override fun onError(e: Throwable) {
                        _randomDishInLoading.value = false
                        _randomDishLoadingErrorOccurred.value = true
                    }
                })
        )

        //Handler for Undeliverable Exceptions
        RxJavaPlugins.setErrorHandler {
            e ->
            if (e is UndeliverableException){
                e.message?.let { Log.e("RxJava", it) }
            }
        }
    }

    /**Method converts data from api to Dish Entity and saves it in db*/
    private fun fetchDish(recipes: RandomDish.Recipes){
        val recipe = recipes.recipes[0]

        _dishSourceUri.value = Uri.parse(recipe.sourceUrl)

        val dishType: String = recipe.dishTypes[0].replaceFirstChar { it.uppercase() }

        var ingredients = ""
        for (i in recipe.extendedIngredients){
            ingredients += "${i.original}\n"
        }

        //Mostly data for this field, came as Html text.
        // For these reason, mandatory parse it
        val steps: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            steps = Html.fromHtml(recipe.instructions, Html.FROM_HTML_MODE_COMPACT).toString()
        } else{
            @Suppress("DEPRECATION")
            steps = Html.fromHtml(recipe.instructions).toString()
        }

        //Prepare entity
        with(recipe){
            _dish.value = Dish(
                image,
                Constants.IMAGE_SOURCE_NETWORK,
                title,
                dishType,
                "Other",
                ingredients = ingredients,
                readyInMinutes,
                steps = steps
            )
        }

        //Insert new dish
        if (dish.value != null){
            insertDishData(dish = dish.value!!)
        }
    }

    fun changeFavoriteState(){
        if (_dish.value != null){
            _dish.value = flipDishFavoriteState(_dish.value!!)

            updateDishData(dish = dish.value!!)
        }
    }
}