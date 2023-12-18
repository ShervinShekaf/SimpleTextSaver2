package com.example.sts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sts.data.ItemData
import com.example.sts.databinding.ItemHomeBinding

class HomeAdapter(val data: ArrayList<ItemData> , val itemEvent:ClickItem) :
    RecyclerView.Adapter<HomeAdapter.FoodViewHolder>() {


    lateinit var binding :ItemHomeBinding
    inner class FoodViewHolder(itemVew :View) :
        RecyclerView.ViewHolder(itemVew) {
        fun bind(itemData: ItemData) {
            binding.txtTitle.text = itemData.txtTitle
            binding.txtDetail.text = itemData.txtDetail

            itemView.setOnClickListener {

                itemEvent.onNoteClicked(data[adapterPosition] , adapterPosition)

            }

            itemView.setOnLongClickListener {

                itemEvent.onNoteLongClicked(data[adapterPosition] , adapterPosition)

                true

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        binding =
            ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding.root)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun addNewItem(newItem: ItemData) {
        data.add(0 , newItem)
        notifyItemInserted(0)
    }

    fun removeNote(oldNote:ItemData , oldPosition:Int) {

        data.remove(oldNote)
        notifyItemRemoved(oldPosition)

    }

    fun updateItem(newItem: ItemData, position: Int) {

        data[position] = newItem
        notifyItemChanged(position)

    }
    interface ClickItem {

        fun onNoteClicked(itemData: ItemData , position: Int)
        fun onNoteLongClicked(itemData: ItemData , position: Int)

    }

}
