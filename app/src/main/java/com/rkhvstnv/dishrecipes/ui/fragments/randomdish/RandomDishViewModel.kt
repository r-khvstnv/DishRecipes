package com.rkhvstnv.dishrecipes.ui.fragments.randomdish

import android.net.Uri
import android.os.Build
import android.text.Html
import androidx.lifecycle.*
import com.rkhvstnv.dishrecipes.ui.fragments.bases.BaseViewModel
import com.rkhvstnv.dishrecipes.models.Dish
import com.rkhvstnv.dishrecipes.models.RandomDish
import com.rkhvstnv.dishrecipes.network.RandomDishService
import com.rkhvstnv.dishrecipes.database.DishRepository
import com.rkhvstnv.dishrecipes.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class RandomDishViewModelFactory(private val repository: DishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return RandomDishViewModel(repository = repository) as T
    }
}
class RandomDishViewModel(private val repository: DishRepository) : BaseViewModel(repository = repository) {
    private val randomDishService = RandomDishService()
    private val compositeDisposable = CompositeDisposable()

    private val _randomDishInLoading = MutableLiveData<Boolean>()
    private val _randomDishLoadingError = MutableLiveData<String>()
    private val _dish = MutableLiveData<Dish>()
    private val _dishSourceUri = MutableLiveData<Uri>()

    val randomDishInLoading: LiveData<Boolean> get() = _randomDishInLoading
    val randomDishLoadingError: LiveData<String> get() = _randomDishLoadingError
    val dish: LiveData<Dish> get() = _dish
    val dishSourceUri get() = _dishSourceUri

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
                        _randomDishLoadingError.value = e.message
                    }

                })
        )
    }

    private fun fetchDish(recipes: RandomDish.Recipes){
        val recipe = recipes.recipes[0]

        _dishSourceUri.value = Uri.parse(recipe.sourceUrl)

        val dishType: String = recipe.dishTypes[0].replaceFirstChar { it.uppercase() }

        var ingredients = ""
        for (i in recipe.extendedIngredients){
            ingredients += "${i.original}\n"
        }

        var steps = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            steps = Html.fromHtml(recipe.instructions, Html.FROM_HTML_MODE_COMPACT).toString()
        } else{
            @Suppress("DEPRECATION")
            steps = Html.fromHtml(recipe.instructions).toString()
        }

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

        if (dish.value != null){
            insert(dish = dish.value!!)
        }
    }

    fun changeFavoriteState(){
        if (_dish.value != null){
            _dish.value = flipDishFavouriteState(_dish.value!!)

            updateDishModel(dish = dish.value!!)
        }
    }
}