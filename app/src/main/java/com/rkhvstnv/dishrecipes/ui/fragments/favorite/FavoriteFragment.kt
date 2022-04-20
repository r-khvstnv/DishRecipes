package com.rkhvstnv.dishrecipes.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkhvstnv.dishrecipes.DishApplication
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.FragmentFavoriteBinding
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.ui.adapters.AllAndFavDishesAdapter
import com.rkhvstnv.dishrecipes.base.BaseFragment
import com.rkhvstnv.dishrecipes.utils.callbacks.ItemDishCallback

class FavoriteFragment : BaseFragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels{
        FavoriteViewModelFactory((activity?.application as DishApplication).repository)
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

        setRecyclerViewStyle()
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
        val adapter = AllAndFavDishesAdapter(this.requireContext(), object : ItemDishCallback {
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
            }

            override fun showOwnerError() {
                showSnackBarErrorMessage(getString(R.string.st_you_are_not_owner))
            }
        })

        binding.rvDishList.adapter = adapter

        viewModel.allFavDishesList.observe(viewLifecycleOwner){
                dishList ->
            dishList.let {
                if (it.isNotEmpty()){
                    adapter.updateDishesList(it.reversed())
                }
            }
        }
    }

    /**Method set preferable style by user.
     * Also change corresponding icon on toolBar*/
    private fun setRecyclerViewStyle(){
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

    //Navigation
    private fun navigateToDishDetails(dishId: Int){
        findNavController().navigate(
            FavoriteFragmentDirections
                .actionNavigationFavoriteToNavigationDishDetails(dishId = dishId)
        )
    }
    private fun navigateToUpdateDish(dishId: Int){
        findNavController().navigate(
            FavoriteFragmentDirections
                .actionNavigationFavoriteToNavigationAddUpdateDish(dishId = dishId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}