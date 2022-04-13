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
import com.rkhvstnv.dishrecipes.ui.fragments.BaseFragment
import com.rkhvstnv.dishrecipes.ui.fragments.addupdatedish.AddUpdateDishViewModel
import com.rkhvstnv.dishrecipes.ui.fragments.addupdatedish.AddUpdateDishViewModelFactory

/**In this fragment is used AddUpdateViewModel.
 * It conditioned to minimal logic functionality of this fragment and
 * primary continuation to AddUpdateFragment from there*/
class DishDetailsFragment : BaseFragment() {
    private var _binding: FragmentDishDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DishDetailsFragmentArgs by navArgs()

    private val viewModel: AddUpdateDishViewModel by activityViewModels {
        AddUpdateDishViewModelFactory((activity?.application as DishApplication).repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dishId = args.dishId
        viewModel.getTmpDish(dishId = dishId)

        viewModel.tmpDish?.observe(viewLifecycleOwner){
            dish ->
            dish.let {
                viewModel.imagePath = it.image

                with(binding){
                    Glide
                        .with(this@DishDetailsFragment)
                        .load(it.image)
                        .into(ivImage)

                    tvType.text = getString(R.string.st_type) + ": " + it.type
                    tvCategory.text = getString(R.string.st_category) + ": " + it.category
                    tvTime.text = it.cookingTime.toString()

                    tvLabel.text = it.label
                    tvIngredients.text = it.ingredients
                    tvSteps.text = it.ingredients

                    if (it.isFavoriteDish){
                        fabFavorite.setImageResource(R.drawable.ic_favorite)
                    } else{
                        fabFavorite.setImageResource(R.drawable.ic_favorite_border_24)
                    }
                }
            }
        }


        binding.fabEditDish.setOnClickListener {
            navigateToAddUpdateFragment()
        }

        binding.fabFavorite.setOnClickListener {
            val dish = viewModel.updateDishFavouriteStateLocally()
            viewModel.updateDishModel(dish = dish)
        }
    }

    private fun navigateToAddUpdateFragment(){
        findNavController().navigate(DishDetailsFragmentDirections.actionNavigationDishDetailsToNavigationAddUpdateDish())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}