package com.rkhvstnv.dishrecipes.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rkhvstnv.dishrecipes.databinding.ItemDishBinding
import com.rkhvstnv.dishrecipes.model.Dish
import com.rkhvstnv.dishrecipes.utils.ItemClickListener

class AllDishAdapter(
    private val context: Context,
    private val itemClickListener: ItemClickListener):
    RecyclerView.Adapter<AllDishAdapter.ViewHolder>() {

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

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(dish.id)
        }
    }

    override fun getItemCount(): Int {
       return dishesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDishesList(list: List<Dish>){
        dishesList = list
        notifyDataSetChanged()
    }
}