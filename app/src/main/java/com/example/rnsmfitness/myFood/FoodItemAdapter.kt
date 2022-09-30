package com.example.rnsmfitness

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
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Activities.FoodDetailsActivity
import com.example.rnsmfitness.Entities.FoodItem

private const val TAG1 = "FoodItemAdapter"


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
       private val calText: TextView
       private val cardView: CardView
       private val servText: TextView

        fun bind(food: FoodItem) {
            nameText.text = food.name
            calText.text = food.calories.toString() + " cal"
            servText.text = food.serving_size.toString() + " g"

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
            servText = itemView.findViewById(R.id.serving_text)

        }



    }



}

