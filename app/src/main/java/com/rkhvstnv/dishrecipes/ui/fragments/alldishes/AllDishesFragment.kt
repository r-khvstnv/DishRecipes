package com.rkhvstnv.dishrecipes.ui.fragments.alldishes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.rkhvstnv.dishrecipes.DishApplication
import com.rkhvstnv.dishrecipes.databinding.FragmentAllDishesBinding

class AllDishesFragment : Fragment() {

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}