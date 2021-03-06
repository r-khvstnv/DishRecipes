package com.rkhvstnv.dishrecipes.alldishes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rkhvstnv.dishrecipes.databinding.ItemFilterBinding
import com.rkhvstnv.dishrecipes.utils.callbacks.ItemFilterCallback

class FilterAdapter(
    private val context: Context,
    private val filterType: String,
    private val paramsList: List<String>,
    private val itemCallback: ItemFilterCallback
    ): RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFilterBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemFilterBinding =
            ItemFilterBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val params = paramsList[position]

        with(holder.binding){
            tvParams.text = params

            tvParams.setOnClickListener {
                itemCallback.onClick(filterType = filterType, params = params)
            }
        }
    }

    override fun getItemCount(): Int {
        return paramsList.size
    }
}