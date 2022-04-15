package com.rkhvstnv.dishrecipes.ui.fragments.alldishes

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.rkhvstnv.dishrecipes.ui.adapters.AllAndFavDishesAdapter
import com.rkhvstnv.dishrecipes.bases.BaseFragment
import com.rkhvstnv.dishrecipes.databinding.FilterDialogBinding
import com.rkhvstnv.dishrecipes.ui.adapters.FilterAdapter
import com.rkhvstnv.dishrecipes.utils.ItemDishClickListener
import com.rkhvstnv.dishrecipes.utils.ItemFilterClickListener


class AllDishesFragment : BaseFragment() {

    private var _binding: FragmentAllDishesBinding? = null
    private val binding get() = _binding!!
    private lateinit var allDishAdapter: AllAndFavDishesAdapter

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

        allDishAdapter = AllAndFavDishesAdapter(this.requireContext(), object : ItemDishClickListener{
            override fun onItemClick(itemId: Int) {
                navigateToDishDetails(itemId)
            }

            override fun onItemFavoriteStateClick(dish: Dish) {
                val tmpDish = viewModel.flipDishFavouriteState(dish = dish)
                viewModel.updateDishModel(tmpDish)
            }

        })
        binding.rvDishList.adapter = allDishAdapter



        observeAllDishes()

        setupToolBar()
    }

    private fun observeAllDishes(){
        viewModel.allDishesList.observe(viewLifecycleOwner){
                dishList ->
            dishList.let {
                if (it.isNotEmpty()){
                    allDishAdapter.updateDishesList(it.reversed())
                }
            }
        }
    }


    private fun setupToolBar(){
        binding.includedMToolBar.mToolBar.setTitle(R.string.st_all_dishes)

        binding.includedMToolBar.mToolBar.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.m_view_style ->{
                    viewModel.flipStyleState()
                    true
                }
                R.id.m_all ->{
                    observeAllDishes()
                    true
                }
                R.id.m_type ->{
                    setupFilterDialog(getString(R.string.st_type))
                    true
                }
                R.id.m_category ->{
                    setupFilterDialog(getString(R.string.st_category))
                    true
                }
                else -> false
            }
        }

        viewModel.isGridStyle.observe(viewLifecycleOwner){
                isGrid ->
            isGrid.let {
                //assign imageView from Top toolBar for recyclerView style
                val stateImageView = binding.includedMToolBar.mToolBar.menu.findItem(R.id.m_view_style)
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

    private fun navigateToDishDetails(dishId: Int){
        findNavController().navigate(
            AllDishesFragmentDirections.actionNavigationAllDishesToNavigationDishDetails(dishId)
        )
    }

    private fun setupFilterDialog(filter: String){
        //prepare all data for rv
        val paramsList: List<String> =
            if (filter == getString(R.string.st_category)){
                resources.getStringArray(R.array.dish_category).toList()
            } else{
            resources.getStringArray(R.array.dish_types).toList()
            }

        val dialog = Dialog(requireContext())
        val dBinding: FilterDialogBinding = FilterDialogBinding.inflate(LayoutInflater.from(this.context))
        dialog.setContentView(dBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dBinding.tvFilterType.text = filter

        val adapter = FilterAdapter(
            requireContext(),
            filterType = filter,
            paramsList = paramsList,
            object : ItemFilterClickListener{
                override fun onClick(filterType: String, params: String) {

                    dialog.dismiss()

                    if (filterType == getString(R.string.st_type)){
                        viewModel.getFilteredDishesListByType(params = params).observe(viewLifecycleOwner){
                            dishesList ->
                            dishesList.let {
                                allDishAdapter.updateDishesList(dishesList)
                            }
                        }
                    } else{
                        viewModel.getFilteredDishesListByCategory(params = params).observe(viewLifecycleOwner){
                                dishesList ->
                            dishesList.let {
                                allDishAdapter.updateDishesList(dishesList)
                            }
                        }
                    }
                }
            })

        dBinding.rvFilter.adapter = adapter
        dBinding.rvFilter.layoutManager =
            LinearLayoutManager(
                this.requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}