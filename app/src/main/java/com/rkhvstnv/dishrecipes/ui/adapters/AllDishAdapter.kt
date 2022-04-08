package com.rkhvstnv.dishrecipes.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.ItemDishBinding
import com.rkhvstnv.dishrecipes.model.Dish

class AllDishAdapter(private val context: Context): RecyclerView.Adapter<AllDishAdapter.ViewHolder>() {
    private var dishesList: List<Dish> = listOf()

    inner class ViewHolder(val binding: ItemDishBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemDishBinding = ItemDishBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = dishesList[position]
        with(holder.binding){
            Glide
                .with(context)
                .load(dish.image)
                .into(ivDishImage)
            tvDishLabel.text = dish.label

        }

    }

    override fun getItemCount(): Int {
       return dishesList.size
    }

    fun updateDishesList(list: List<Dish>){
        dishesList = list
        notifyDataSetChanged()
    }
}