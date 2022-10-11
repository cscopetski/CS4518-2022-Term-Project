package com.example.rnsmfitness

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Activities.FoodDetailsActivity
import com.example.rnsmfitness.Activities.USDAFoodDetailsActivity
import com.example.rnsmfitness.Entities.USDAFoodItem
import java.util.*
import kotlin.collections.ArrayList

private const val TAG1 = "FoodItemAdapter"



class USDAFoodItemAdapter (val activity: Activity, private val foods: List<USDAFoodItem>) : RecyclerView.Adapter<USDAFoodItemAdapter.ViewHolder>() {

    var foodsCopy: ArrayList<USDAFoodItem> = ArrayList(foods)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foodsCopy[position])
    }

    override fun getItemCount(): Int {
        return foodsCopy.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView
        private val calText: TextView
        private val cardView: CardView
        private val servText: TextView

        fun bind(food: USDAFoodItem) {
            nameText.text = food.name
            calText.text = food.calories.toString() + " cal"
            servText.text = food.serving_size.toString() + " g"

            cardView.setOnClickListener {

                Log.d(TAG1, "button pressed")

                val valueList = ArrayList<String>()

                valueList.add(food.protein.toString())
                valueList.add(food.fat.toString())
                valueList.add(food.carbs.toString())
                valueList.add(food.name)
                valueList.add(food.serving_size.toString())

                val intent = Intent(activity, USDAFoodDetailsActivity::class.java)

                intent.putStringArrayListExtra("valueList", valueList)
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
