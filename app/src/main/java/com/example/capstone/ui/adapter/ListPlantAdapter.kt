package com.example.capstone.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.databinding.NutritionRowItemBinding

class ListPlantAdapter(private val listPlant: ArrayList<Plant>): RecyclerView.Adapter<ListPlantAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPlantAdapter.ListViewHolder {
        val binding = NutritionRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListPlantAdapter.ListViewHolder, position: Int) {
        val list = listPlant[position]
        holder.bind(list)
    }

    override fun getItemCount(): Int = listPlant.size

    class ListViewHolder(private val binding: NutritionRowItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(plant: Plant) {
            binding.imgPoster.setImageResource(plant.photo)
            binding.tvItemTitle.text = plant.name
            binding.tvItemDescription.text = plant.description
        }
    }

}