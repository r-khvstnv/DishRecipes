package com.rkhvstnv.dishrecipes.ui.fragments.randomdish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rkhvstnv.dishrecipes.bases.BaseViewModel
import com.rkhvstnv.dishrecipes.model.entities.RandomDish
import com.rkhvstnv.dishrecipes.model.network.RandomDishService
import com.rkhvstnv.dishrecipes.model.room.DishRepository
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
class RandomDishViewModel(repository: DishRepository) : BaseViewModel(repository = repository) {
    private val randomDishService = RandomDishService()
    private val compositeDisposable = CompositeDisposable()

    private val _randomDishInLoading = MutableLiveData<Boolean>()
    private val _randomDishResponse = MutableLiveData<RandomDish.Recipes>()
    private val _randomDishLoadingError = MutableLiveData<String>()

    val randomDishInLoading: LiveData<Boolean> get() = _randomDishInLoading
    val randomDishResponse: LiveData<RandomDish.Recipes> get() = _randomDishResponse
    val randomDishLoadingError: LiveData<String> get() = _randomDishLoadingError

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
                        _randomDishResponse.value = t
                    }

                    override fun onError(e: Throwable) {
                        _randomDishInLoading.value = false
                        _randomDishLoadingError.value = e.message
                    }

                })
        )
    }
}