package com.rkhvstnv.dishrecipes.alldishes

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.rkhvstnv.dishrecipes.databinding.DialogFilterBinding
import com.rkhvstnv.dishrecipes.databinding.FragmentAllBinding
import com.rkhvstnv.dishrecipes.app.models.Dish
import com.rkhvstnv.dishrecipes.utils.appComponent
import com.rkhvstnv.dishrecipes.utils.callbacks.ItemDishCallback
import com.rkhvstnv.dishrecipes.utils.callbacks.ItemFilterCallback
import javax.inject.Inject


class AllFragment : BaseFragment() {
    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!
    private lateinit var allDishAdapter: AllAndFavDishesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AllViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.allComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolBar()
        setupRecyclerViewAdapter()

        observeAllDishes()

        viewModel.isGridStyle.observe(viewLifecycleOwner){
                isGrid ->
            isGrid.let {
                //assign imageView from Top toolBar for recyclerView style
                val stateImageView =
                    binding.includedMToolBar.mToolBar.menu.findItem(R.id.m_view_style)

                //change icon and layoutManager depending on received data
                if (isGrid){
                    binding.rvDishList.layoutManager =
                        GridLayoutManager(
                            this.requireContext(),
                            2
                        )
                    stateImageView.setIcon(R.drawable.ic_view_grid_24)
                } else{
                    binding.rvDishList.layoutManager =
                        LinearLayoutManager(
                            this.requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    stateImageView.setIcon(R.drawable.ic_view_linear_24)
                }
            }
        }
    }


    /**Setup toolBar and corresponding actions on menu items click*/
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
    }

    /**Setup dishes recyclerView with corresponding callbacks*/
    private fun setupRecyclerViewAdapter(){
        allDishAdapter = AllAndFavDishesAdapter(this.requireContext(), object :
            ItemDishCallback {
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

        binding.rvDishList.adapter = allDishAdapter
    }


    private fun observeAllDishes(){
        removeAllObservers()

        viewModel.allDishesList.observe(viewLifecycleOwner){
                dishList ->
            dishList.let {
                if (it.isNotEmpty()){
                    allDishAdapter.updateDishesList(it.reversed())
                    binding.includedMToolBar.mToolBar.title = getString(R.string.st_all_dishes)
                }
            }
        }
    }

    /**Method prevents from data collision,
     * due to all of them pass data to the same list.
     * Otherwise user will see all data or filtered by some type,
     * if previously any dish was deleted
     * (Due to dish has type/category parameter itself, for which the observation takes place)*/
    private fun removeAllObservers(){
        viewModel.allDishesList.removeObservers(viewLifecycleOwner)
        viewModel.dishListByType.removeObservers(viewLifecycleOwner)
        viewModel.dishListByCategory.removeObservers(viewLifecycleOwner)
    }

    private fun navigateToDishDetails(dishId: Int){
        findNavController().navigate(
            AllFragmentDirections
                .actionNavigationAllToNavigationDetails(dishId = dishId)
        )
    }

    private fun navigateToUpdateDish(dishId: Int){
        findNavController().navigate(
            AllFragmentDirections
                .actionNavigationAllToNavigationAddUpdate(dishId = dishId)
        )
    }


    /**Next method prepares dialog with users filter type (input parameter)
     * Filter subtypes are received from viewModel*/
    private fun setupFilterDialog(filter: String){
        //Choose list of subtypes
        val paramsList: List<String> = if (filter == getString(R.string.st_type)){
            viewModel.dishTypes.value!!
        } else {
            viewModel.dishCategories.value!!
        }

        if (paramsList.isEmpty()){
            showSnackBarErrorMessage(getString(R.string.st_no_dishes))
        }

        //Setup dialog
        val dialog = Dialog(requireContext())
        val dBinding: DialogFilterBinding =
            DialogFilterBinding.inflate(LayoutInflater.from(this.context))
        dialog.setContentView(dBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //Set dialog title
        dBinding.tvFilterType.text = filter

        //Setup inner recyclerView with corresponding callbacks
        val adapter = FilterAdapter(
            requireContext(),
            filterType = filter,
            paramsList = paramsList,
            object : ItemFilterCallback {
                override fun onClick(filterType: String, params: String) {

                    dialog.dismiss()

                    removeAllObservers()

                    //Change toolBar title
                    binding.includedMToolBar.mToolBar.title = params

                    //Show filtered dishesList by chosen type
                    if (filterType == getString(R.string.st_type)){
                        viewModel.getFilteredDishesListByType(params = params)

                        viewModel.dishListByType.observe(viewLifecycleOwner){
                                dishesList ->
                            dishesList.let {
                                allDishAdapter.updateDishesList(dishesList.reversed())
                            }
                        }
                    } else{
                        viewModel.getFilteredDishesListByCategory(params = params)

                        viewModel.dishListByCategory.observe(viewLifecycleOwner){
                                dishesList ->
                            dishesList.let {
                                allDishAdapter.updateDishesList(dishesList.reversed())
                            }
                        }
                    }
                }
            })

        dBinding.rvFilter.adapter = adapter
        dBinding.rvFilter.layoutManager = LinearLayoutManager(
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