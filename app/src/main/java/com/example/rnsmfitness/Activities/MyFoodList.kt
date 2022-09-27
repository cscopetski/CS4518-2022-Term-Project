package com.example.rnsmfitness.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rnsmfitness.Entities.FoodItem
import com.example.rnsmfitness.R

public fun getFoods(): List<FoodItem> {

    var id: Int
    var user_id: Int
    var title: String
    var protein: Int
    var carbs: Int
    var fat: Int
    var calories: Int
    var serving_size: Double
    val foods = ArrayList<FoodItem>()

    for (i in 1..20){
        id = i
        user_id = 100
        title = "Food $i"
        protein = i
        carbs = i
        fat = i
        calories = 4 * protein + 4 * carbs + 9 * fat
        serving_size = i.toDouble()


        foods.add(
            FoodItem(id, user_id, title, true,protein, fat, carbs, calories, serving_size)
        )
    }

    return foods
}

class MyFoodList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_food_list)
    }


}