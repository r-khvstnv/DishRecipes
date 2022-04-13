package com.rkhvstnv.dishrecipes.ui.fragments.alldishes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkhvstnv.dishrecipes.DishApplication
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.FragmentAllDishesBinding
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.ui.adapters.AllDishAdapter
import com.rkhvstnv.dishrecipes.bases.BaseFragment
import com.rkhvstnv.dishrecipes.utils.ItemClickListener


class AllDishesFragment : BaseFragment() {

    private var _binding: FragmentAllDishesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AllDishesViewModel by viewModels {
        AllDishViewModelFactory((activity?.application as DishApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllDishesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AllDishAdapter(this.requireContext(), object : ItemClickListener{
            override fun onItemClick(itemId: Int) {
                navigateToDishDetails(itemId)
            }

            override fun onItemFavoriteStateClick(dish: Dish) {
                val tmpDish = viewModel.flipDishFavouriteState(dish = dish)
                viewModel.updateDishModel(tmpDish)
            }

        })
        binding.rvDishList.adapter = adapter

        viewModel.isGridStyle.observe(viewLifecycleOwner){
                isGrid ->
            isGrid.let {
                //assign imageView from Top toolBar for recyclerView style
                val stateImageView = binding.mToolBar.menu.findItem(R.id.m_view_style)
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

        viewModel.allDishesList.observe(viewLifecycleOwner){
                dishList ->
            dishList.let {
                if (it.isNotEmpty()){
                    adapter.updateDishesList(it.reversed())
                }
            }
        }

        setupToolBarListener()
    }


    private fun setupToolBarListener(){
        binding.mToolBar.setOnMenuItemClickListener{
            if (it.itemId == R.id.m_view_style){
                viewModel.flipStyleState()
                return@setOnMenuItemClickListener true
            } else{
                return@setOnMenuItemClickListener false
            }
        }
    }

    private fun navigateToDishDetails(dishId: Int){
        findNavController().navigate(
            AllDishesFragmentDirections.actionNavigationAllDishesToNavigationDishDetails(dishId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}