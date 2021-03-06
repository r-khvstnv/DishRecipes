package com.rkhvstnv.dishrecipes.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.app.presenters.BaseFragment
import com.rkhvstnv.dishrecipes.databinding.FragmentFavoriteBinding
import com.rkhvstnv.dishrecipes.app.models.Dish
import com.rkhvstnv.dishrecipes.alldishes.AllAndFavDishesAdapter
import com.rkhvstnv.dishrecipes.utils.appComponent
import com.rkhvstnv.dishrecipes.utils.callbacks.ItemDishCallback

import javax.inject.Inject

class FavoriteFragment : BaseFragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<FavoriteViewModel> { viewModelFactory }

    private lateinit var favoriteAdapter: AllAndFavDishesAdapter

    override fun onAttach(context: Context) {
        context.appComponent.favoriteComponent().create().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //In this fragment, filter functionality is unnecessary
        binding.includedMToolBar.mToolBar.menu.findItem(R.id.m_filter).isVisible = false

        setupToolBar()
        setupRecyclerViewAdapter()

        viewModel.allFavDishesList.observe(viewLifecycleOwner){
                dishList ->
            dishList.let {
                if (it.isNotEmpty()){
                    favoriteAdapter.updateDishesList(it.reversed())
                }
            }
        }
        viewModel.isGridStyle.observe(viewLifecycleOwner){
                isGrid ->
            isGrid.let {
                //assign imageView from Top toolBar for recyclerView style
                val stateImageView =
                    binding.includedMToolBar.mToolBar.menu.findItem(R.id.m_view_style)
                //change icon and layoutManager depending on received data
                if (isGrid){
                    binding.rvDishList.layoutManager =
                        GridLayoutManager(this.requireContext(), 2)
                    stateImageView.setIcon(R.drawable.ic_view_grid_24)
                } else{
                    binding.rvDishList.layoutManager =
                        LinearLayoutManager(
                            this.requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false)
                    stateImageView.setIcon(R.drawable.ic_view_linear_24)
                }
            }
        }
    }

    private fun setupToolBar(){
        binding.includedMToolBar.mToolBar.setTitle(R.string.st_favorite)

        binding.includedMToolBar.mToolBar.setOnMenuItemClickListener{
            if (it.itemId == R.id.m_view_style){
                viewModel.flipStyleState()
                return@setOnMenuItemClickListener true
            } else{
                return@setOnMenuItemClickListener false
            }
        }
    }

    /**Method prepare rv adapter with corresponding callbacks
     * and also observe dishesList*/
    private fun setupRecyclerViewAdapter(){
        favoriteAdapter = AllAndFavDishesAdapter(
            this.requireContext(),
            object : ItemDishCallback {

            override fun onViewClick(itemId: Int) {
                navigateToDishDetails(itemId)
            }

            override fun onFavoriteStateClick(dish: Dish) {
                val tmpDish = viewModel.flipDishFavoriteState(dish = dish)
                viewModel.updateDishData(tmpDish)
            }

            override fun onEditClick(itemId: Int) {
                navigateToUpdateDish(itemId)
            }

            override fun onDeleteClick(dish: Dish) {
                deleteFile(dish.image, dish.imageSource)
                viewModel.deleteDishData(dish = dish)
                showSnackBarPositiveMessage(getString(R.string.st_successfully_deleted))
            }

            override fun showOwnerError() {
                showSnackBarErrorMessage(getString(R.string.st_you_are_not_owner))
            }
        })

        binding.rvDishList.adapter = favoriteAdapter
    }

    //Navigation
    private fun navigateToDishDetails(dishId: Int){
        findNavController().navigate(
            FavoriteFragmentDirections
                .actionNavigationFavoriteToNavigationDetails(dishId = dishId)
        )
    }
    private fun navigateToUpdateDish(dishId: Int){
        findNavController().navigate(
            FavoriteFragmentDirections
                .actionNavigationFavoriteToNavigationAddUpdate(dishId = dishId)
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}