package com.rkhvstnv.dishrecipes.ui.fragments.addupdatedish

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rkhvstnv.dishrecipes.R

class AddUpdateDishFragment : Fragment() {

    companion object {
        fun newInstance() = AddUpdateDishFragment()
    }

    private lateinit var viewModel: AddUpdateDishViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_update_dish, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddUpdateDishViewModel::class.java]
        // TODO: Use the ViewModel
    }

}