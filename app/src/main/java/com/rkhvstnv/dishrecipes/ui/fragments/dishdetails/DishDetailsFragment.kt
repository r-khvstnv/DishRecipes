package com.rkhvstnv.dishrecipes.ui.fragments.dishdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rkhvstnv.dishrecipes.DishApplication
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.FragmentDishDetailsBinding
import com.rkhvstnv.dishrecipes.base.BaseFragment
import com.rkhvstnv.dishrecipes.di.OldViewModelFactory
import com.rkhvstnv.dishrecipes.ui.fragments.addupdatedish.AddUpdateDishViewModel
import com.rkhvstnv.dishrecipes.utils.Constants

/**In this fragment is used AddUpdateViewModel.
 * It conditioned to minimal logic functionality of this fragment and
 * primary continuation to AddUpdateFragment from there*/
class DishDetailsFragment : BaseFragment() {
    private var _binding: FragmentDishDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DishDetailsFragmentArgs by navArgs()

    private val viewModel: AddUpdateDishViewModel by activityViewModels {
        OldViewModelFactory(AddUpdateDishViewModel((activity?.application as DishApplication).repository))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.let {
            if (args.dishId != Constants.DEF_ARGS_INT){
                viewModel.assignTmpDish(it.dishId)
            }
        }

        observeDish()

        binding.fabEditDish.setOnClickListener {
            navigateToAddUpdateFragment()
        }
        binding.iDishDetails.fabFavorite.setOnClickListener {
            val dish = viewModel.flipDishFavoriteState(viewModel.tmpDish!!.value!!)
            viewModel.updateDishData(dish = dish)
        }
        binding.fabDeleteDish.setOnClickListener {
            requestDishDeleting()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeDish(){
        viewModel.tmpDish?.observe(viewLifecycleOwner){
                dish ->
            dish.let {
                viewModel.imagePath = it.image

                with(binding.iDishDetails){
                    Glide
                        .with(this@DishDetailsFragment)
                        .load(it.image)
                        .centerCrop()
                        .into(ivImage)

                    tvType.text = getString(R.string.st_type) + ": " + it.type
                    tvCategory.text = getString(R.string.st_category) + ": " + it.category
                    tvTime.text = it.cookingTime.toString()

                    tvLabel.text = it.label
                    tvIngredients.text = it.ingredients
                    tvSteps.text = it.steps

                    if (it.isFavoriteDish){
                        fabFavorite.setImageResource(R.drawable.ic_favorite)
                    } else{
                        fabFavorite.setImageResource(R.drawable.ic_favorite_border_24)
                    }

                    //Only user dishes are available for editing
                    if (it.imageSource == Constants.IMAGE_SOURCE_NETWORK){
                        binding.fabEditDish.visibility = View.GONE
                    }
                }
            }
        }
    }

    /** Navigates to fragment where user can update dish data.
     * Additional parameters is not needed, due to This and Next Fragment use Common ViewModel*/
    private fun navigateToAddUpdateFragment(){
        findNavController().navigate(
            DishDetailsFragmentDirections.actionNavigationDishDetailsToNavigationAddUpdateDish()
        )
    }

    private fun requestDishDeleting(){
        deleteFile(viewModel.tmpDish!!.value!!.image, viewModel.tmpDish!!.value!!.imageSource)
        viewModel.deleteDishData(viewModel.tmpDish!!.value!!)
        navigateToAllDishes(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.tmpDish = null
    }
}