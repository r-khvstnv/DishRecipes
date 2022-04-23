package com.rkhvstnv.dishrecipes.ui.fragments.randomdish

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.rkhvstnv.dishrecipes.DishApplication
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.base.BaseFragment
import com.rkhvstnv.dishrecipes.databinding.FragmentRandomDishBinding
import com.rkhvstnv.dishrecipes.di.OldViewModelFactory
import com.rkhvstnv.dishrecipes.ui.fragments.alldishes.AllDishesViewModel
import com.rkhvstnv.dishrecipes.utils.appComponent
import javax.inject.Inject

//todo implement dagger
class RandomDishFragment : BaseFragment() {
    private var _binding: FragmentRandomDishBinding? = null
    private val binding get() = _binding!!

    /*private val viewModel: RandomDishViewModel by viewModels{
        OldViewModelFactory(RandomDishViewModel((activity?.application as DishApplication).repository))
    }*/

    @Inject
    lateinit var viewModel: RandomDishViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeRandomDish()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshRandomDish()
        }

        binding.inDishDetails.fabFavorite.setOnClickListener {
            viewModel.changeFavoriteState()
        }

        binding.fabSource.setOnClickListener {
            openDishSource()
        }

        viewModel.refreshRandomDish()

    }

    @SuppressLint("SetTextI18n")
    private fun observeRandomDish(){
        //Loading status
        viewModel.randomDishInLoading.observe(viewLifecycleOwner){
            inLoading ->
            inLoading.let {
                if (it){
                    binding.flContainer.visibility = View.GONE
                } else{
                    binding.flContainer.visibility = View.VISIBLE
                }
                binding.swipeRefreshLayout.isRefreshing = it
            }
        }
        //Dish data
        viewModel.dish.observe(viewLifecycleOwner){
                dish ->
            dish.let {
                with(binding.inDishDetails){
                    Glide
                        .with(this@RandomDishFragment)
                        .load(it.image)
                        .centerCrop()
                        .into(ivImage)

                    tvType.text = getString(R.string.st_type) + ": " + it.type
                    tvCategory.text = getString(R.string.st_category) + ": " + getString(R.string.st_other)
                    tvTime.text = it.cookingTime.toString()

                    tvLabel.text = it.label
                    tvSteps.text = it.steps
                    tvIngredients.text = it.ingredients
                }
            }
        }
        //Request Errors
        viewModel.randomDishLoadingError.observe(viewLifecycleOwner){
                error ->
            showSnackBarErrorMessage(error)
        }

    }

    private fun openDishSource(){
        val uri = viewModel.dishSourceUri.value
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}