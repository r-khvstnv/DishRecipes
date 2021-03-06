package com.rkhvstnv.dishrecipes.dishdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.app.presenters.BaseFragment
import com.rkhvstnv.dishrecipes.databinding.FragmentDetailsBinding
import com.rkhvstnv.dishrecipes.utils.Constants
import com.rkhvstnv.dishrecipes.utils.appComponent
import javax.inject.Inject

class DetailsFragment : BaseFragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<DetailsViewModel> {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        context.appComponent.detailsComponent().create().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.let {
            if (it.dishId != Constants.DEF_ARGS_INT){
                viewModel.setDish(it.dishId)
            }
        }

        binding.fabEditDish.setOnClickListener {
            navigateToAddUpdateFragment()
        }
        binding.iDishDetails.fabFavorite.setOnClickListener {
            val dish = viewModel.flipDishFavoriteState(viewModel.dish?.value!!)
            viewModel.updateDishData(dish = dish)
        }
        binding.fabDeleteDish.setOnClickListener {
            requestDishDeleting()
        }


        viewModel.dish?.observe(viewLifecycleOwner){
                dish ->
            dish?.let {

                with(binding.iDishDetails){
                    Glide
                        .with(this@DetailsFragment)
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
            DetailsFragmentDirections
                .actionNavigationDetailsToNavigationAddUpdate(viewModel.dish?.value!!.id)
        )
    }

    private fun requestDishDeleting(){
        deleteFile(viewModel.dish!!.value!!.image, viewModel.dish!!.value!!.imageSource)
        viewModel.deleteDishData(viewModel.dish!!.value!!)
        showSnackBarPositiveMessage(getString(R.string.st_successfully_deleted))
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}