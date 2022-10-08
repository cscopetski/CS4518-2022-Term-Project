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
import com.example.rnsmfitness.Activities.DailyFoodDetailsActivity
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
        private val nameText: TextView
        private val servingText: TextView
        private val calText: TextView
        private val cardView: CardView

        fun bind(food: DailyFoodItem) {
            nameText.text = food.name
            servingText.text = Math.round(food.serving_size*food.quantity).toString() + " g"
            val updatedCalories = (Math.round(food.quantity*food.protein*4) + Math.round(food.quantity * food.carbs*4) + Math.round(food.quantity * food.fat*9))
            calText.text = updatedCalories.toString() + " cal"

            cardView.setOnClickListener {

                val intent = Intent(activity, DailyFoodDetailsActivity::class.java)

                val valueList = ArrayList<String>()

                valueList.add(food.protein.toString())
                valueList.add(food.fat.toString())
                valueList.add(food.carbs.toString())
                valueList.add(food.name)
                valueList.add(food.serving_size.toString())
                valueList.add(food.id.toString())
                valueList.add(food.quantity.toString())
                valueList.add(food.meal)

                intent.putExtra("valueList", valueList)
                activity.startActivity(intent)

            }



        }

        init {
            nameText = itemView.findViewById(R.id.item_title)
            calText = itemView.findViewById(R.id.item_calories)
            cardView = itemView.findViewById(R.id.card)
            servingText = itemView.findViewById(R.id.serving_text)
        }



    }



}