package com.example.sts.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.sts.data.ItemData
import com.example.sts.databinding.ItemHomeBinding

class HomeAdapter(val data: ArrayList<ItemData>) :
    RecyclerView.Adapter<HomeAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(itemData: ItemData) {

            binding.txtTitle.text = itemData.txtTitle
            binding.txtDetail.text = itemData.txtPrice
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding =
            ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun addNewFood(newFood: ItemData) {
        data.add(0 , newFood)
        notifyItemInserted(0)
    }
}
