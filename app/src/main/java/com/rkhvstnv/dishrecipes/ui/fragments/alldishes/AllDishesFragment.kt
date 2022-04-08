package com.rkhvstnv.dishrecipes.ui.fragments.alldishes

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rkhvstnv.dishrecipes.DishApplication
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.FragmentAllDishesBinding
import com.rkhvstnv.dishrecipes.ui.adapters.AllDishAdapter
import com.rkhvstnv.dishrecipes.ui.fragments.BaseFragment
import com.rkhvstnv.dishrecipes.utils.ItemClickListener

//todo visibility sort - linear/grid
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

        })
        binding.rvDishList.adapter = adapter

        viewModel.isGridStyle.observe(viewLifecycleOwner){
                isGrid ->
            isGrid.let {
                val stateImageView = binding.mToolBar.menu.findItem(R.id.m_view_style)
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
                } else{
                    showSnackBarErrorMessage(getString(R.string.st_no_dishes))
                }
            }
        }

        setupToolBarListener()
    }

    private fun setupToolBarListener(){
        binding.mToolBar.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.m_view_style ->{
                    viewModel.flipStyleState()
                    true
                }
                R.id.m_filter ->{
                    //todo some filter
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateToDishDetails(dishId: Int){
        findNavController().navigate(AllDishesFragmentDirections.actionNavigationAllDishesToDishDetailsFragment(dishId))
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}