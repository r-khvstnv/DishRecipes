package com.rkhvstnv.dishrecipes.random

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.app.presenters.BaseFragment
import com.rkhvstnv.dishrecipes.databinding.FragmentRandomBinding
import com.rkhvstnv.dishrecipes.utils.appComponent
import javax.inject.Inject


class RandomFragment : BaseFragment() {
    private var _binding: FragmentRandomBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<RandomViewModel> { viewModelFactory }


    override fun onAttach(context: Context) {
        context.appComponent.randomComponent().create().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshRandomDish()
        }

        binding.inDishDetails.fabFavorite.setOnClickListener {
            viewModel.changeFavoriteState()
        }

        binding.fabSource.setOnClickListener {
            openDishSource()
        }


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
                        .with(this@RandomFragment)
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
        //Request Error
        viewModel.randomDishLoadingErrorOccurred.observe(viewLifecycleOwner){
                isErrorOccurred ->
            if (isErrorOccurred){
                showSnackBarErrorMessage(getString(R.string.st_some_error))
            }
        }
    }

    private fun openDishSource(){
        val uri = viewModel.dishSourceUri.value
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}