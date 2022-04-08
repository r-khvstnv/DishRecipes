package com.rkhvstnv.dishrecipes.ui.fragments.dishdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
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

    private val viewModel: AddUpdateDishViewModel by viewModels {
        AddUpdateDishViewModelFactory((activity?.application as DishApplication).repository)
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
        val dishId = args.dishId
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}