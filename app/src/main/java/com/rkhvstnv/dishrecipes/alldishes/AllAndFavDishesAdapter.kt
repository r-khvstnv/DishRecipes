package com.rkhvstnv.dishrecipes.alldishes

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rkhvstnv.dishrecipes.R
import com.rkhvstnv.dishrecipes.databinding.ItemDishBinding
import com.rkhvstnv.dishrecipes.app.domain.Dish
import com.rkhvstnv.dishrecipes.utils.Constants
import com.rkhvstnv.dishrecipes.utils.callbacks.ItemDishCallback

class AllAndFavDishesAdapter(
    private val context: Context,
    private val itemCallback: ItemDishCallback
): RecyclerView.Adapter<AllAndFavDishesAdapter.ViewHolder>() {

    private var dishesList: List<Dish> = listOf()

    inner class ViewHolder(val binding: ItemDishBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemDishBinding = ItemDishBinding
            .inflate(LayoutInflater.from(context), parent, false)
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

            //set corresponding icon to favorite state
            if (dish.isFavoriteDish){
                ivFavoriteState.setImageResource(R.drawable.ic_favorite)
            } else{
                ivFavoriteState.setImageResource(R.drawable.ic_favorite_border_24)
            }

            ivFavoriteState.setOnClickListener {
                itemCallback.onFavoriteStateClick(dish = dish)
            }

            ivMore.setOnClickListener {
                setupPopupMenu(it, dish = dish)
            }
        }

        holder.itemView.setOnClickListener {
            itemCallback.onViewClick(dish.id)
        }

        holder.itemView.setOnLongClickListener {
            setupPopupMenu(holder.binding.ivMore, dish = dish)
            true
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

    /**Method setup menu with corresponding callbacks.
     * Preferable anchor is icon More*/
    private fun setupPopupMenu(view: View, dish: Dish){
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.menu_adapter_dropdown, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.m_edit_dish ->{
                    if (dish.imageSource != Constants.IMAGE_SOURCE_NETWORK){
                        itemCallback.onEditClick(dish.id)
                    } else{
                        itemCallback.showOwnerError()
                    }
                    true
                }
                R.id.m_delete_dish ->{
                    itemCallback.onDeleteClick(dish)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}