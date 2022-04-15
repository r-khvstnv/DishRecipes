package com.rkhvstnv.dishrecipes.ui.fragments.randomdish

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rkhvstnv.dishrecipes.DishApplication
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.bases.BaseFragment
import com.rkhvstnv.dishrecipes.databinding.FragmentRandomDishBinding
//todo add source link
//todo add saving for api recipes
//todo implement dagger
class RandomDishFragment : BaseFragment() {
    private var _binding: FragmentRandomDishBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RandomDishViewModel by viewModels{
        RandomDishViewModelFactory((activity?.application as DishApplication).repository)
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

        viewModel.refreshRandomDish()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshRandomDish()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun observeRandomDish(){
        viewModel.randomDishInLoading.observe(viewLifecycleOwner){
            inLoading ->
            inLoading.let {
                if (it){
                    binding.llContainer.visibility = View.GONE
                } else{
                    binding.llContainer.visibility = View.VISIBLE
                }
                binding.swipeRefreshLayout.isRefreshing = it
            }
        }


        viewModel.randomDishResponse.observe(viewLifecycleOwner){
                recipes ->
            recipes.recipes[0].let {
                with(binding.includedDishDetails){
                    Glide
                        .with(this@RandomDishFragment)
                        .load(it.image)
                        .centerCrop()
                        .into(ivImage)

                    tvType.text = getString(R.string.st_type) + ": " + it.dishTypes[0]
                    tvCategory.text = getString(R.string.st_category) + ": " + getString(R.string.st_other)
                    tvTime.text = it.readyInMinutes.toString()

                    tvLabel.text = it.title
                    tvSteps.text = it.instructions

                    var ingredients: String = ""
                    for (i in it.extendedIngredients){
                        ingredients += "${i.original}\n"
                    }
                    tvIngredients.text = ingredients
                }
            }
        }

        viewModel.randomDishLoadingError.observe(viewLifecycleOwner){
                error ->
            showSnackBarErrorMessage(error)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}