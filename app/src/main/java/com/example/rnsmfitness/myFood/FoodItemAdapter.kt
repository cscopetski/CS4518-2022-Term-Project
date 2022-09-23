package com.example.rnsmfitness

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Entities.FoodItem


class FoodItemAdapter (val activity: Activity, private val foods: List<FoodItem>) : RecyclerView.Adapter<FoodItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foods[position])
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView
        private val proteinText: TextView
        private val carbText: TextView
        private val fatText: TextView
        private val cardView: CardView

        fun bind(food: FoodItem) {
            nameText.text = food.name
            proteinText.text = food.protein.toString()
            carbText.text = food.carbs.toString()
            fatText.text = food.fat.toString()

            cardView.setOnClickListener {
                Toast.makeText(activity,food.name + ": \nTotal Calories: " + (food.protein*4 + food.carbs*4 + food.fat*9), Toast.LENGTH_SHORT).show()
                true
            }

        }

        init {
            nameText = itemView.findViewById(R.id.item_title)
            proteinText = itemView.findViewById(R.id.item_protein)
            carbText = itemView.findViewById(R.id.item_carbs)
            fatText = itemView.findViewById(R.id.item_fat)
            cardView = itemView.findViewById(R.id.card)
        }
    }

}

