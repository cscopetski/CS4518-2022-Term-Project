package com.example.rnsmfitness.myFood

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Activities.FoodDetailsActivity
import com.example.rnsmfitness.Entities.FoodItem
import com.example.rnsmfitness.Entities.DailyFoodItem
import com.example.rnsmfitness.R

private const val TAG1 = "DailyFoodItemAdapter"


class DailyFoodItemAdapter (val activity: Activity, private val foods: List<DailyFoodItem>) : RecyclerView.Adapter<DailyFoodItemAdapter.ViewHolder>() {

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
        private val launchButton: Button
        private val nameText: TextView
        private val calText: TextView
        private val cardView: CardView

        fun bind(food: DailyFoodItem) {
            nameText.text = food.name
            val updatedCalories = Math.round((food.protein*4 + food.carbs*4 + food.fat*9)*food.quantity)
            calText.text = updatedCalories.toString()

            cardView.setOnClickListener {
                Toast.makeText(activity,food.name + ": \nTotal Calories: " + (food.protein*4 + food.carbs*4 + food.fat*9), Toast.LENGTH_SHORT).show()

                Log.d(TAG1, "button pressed")

                val valueList = ArrayList<Int>()

                valueList.add(food.protein)
                valueList.add(food.fat)
                valueList.add(food.carbs)

                val intent = Intent(activity, FoodDetailsActivity::class.java)

                intent.putIntegerArrayListExtra("valueList", valueList)
                activity.startActivity(intent)

            }



        }

        init {
            nameText = itemView.findViewById(R.id.item_title)
            calText = itemView.findViewById(R.id.item_calories)
            cardView = itemView.findViewById(R.id.card)
            launchButton = itemView.findViewById(R.id.item_launch_button)
        }



    }



}