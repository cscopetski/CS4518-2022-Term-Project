package com.example.rnsmfitness.Activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.rnsmfitness.R

class DailyFoodDetailsActivity : AppCompatActivity() {

    lateinit var detailsCalories: TextView
    lateinit var detailsProtein: TextView
    lateinit var detailsCarbs: TextView
    lateinit var detailsFat: TextView
    lateinit var detailsName: TextView
    lateinit var closeButton: Button
    lateinit var editButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_food_details)

        detailsCalories = findViewById(R.id.food_detail_calories)
        detailsProtein = findViewById(R.id.food_detail_protein)
        detailsCarbs = findViewById(R.id.food_detail_carbs)
        detailsFat = findViewById(R.id.food_detail_fats)
        detailsName = findViewById(R.id.food_detail_name)
        closeButton = findViewById(R.id.close_button)
        editButton = findViewById(R.id.edit_button)


        val protein: String = intent.getStringArrayListExtra("valueList")!![0]
        val fat: String = intent.getStringArrayListExtra("valueList")!![1]
        val carbs: String = intent.getStringArrayListExtra("valueList")!![2]

        val name: String = intent.getStringArrayListExtra("valueList")!![3]
        val servingSize: String = intent.getStringArrayListExtra("valueList")!![4].toString()
        val foodID: String = intent.getStringArrayListExtra("valueList")!![5]
        val quantity: Double = intent.getStringArrayListExtra("valueList")!![6].toDouble()
        val meal: String = intent.getStringArrayListExtra("valueList")!![7]
        val calories: Int =
            Math.round(quantity*((protein.toInt() * 4) + (carbs.toInt() * 4) + (fat.toInt() * 9))).toInt()

        detailsProtein.text = Math.round(quantity*protein.toInt()).toString() + "g"
        detailsCarbs.text = Math.round(quantity*carbs.toInt()).toString() + "g"
        detailsFat.text = Math.round(quantity*fat.toInt()).toString() + "g"
        detailsCalories.text = calories.toString() + " cal"
        detailsName.text = name

        //val detailsList = intent.getStringArrayListExtra("valueList")
        val detailsList = ArrayList<String>()
        detailsList.add(protein)
        detailsList.add(fat)
        detailsList.add(carbs)
        detailsList.add(name)
        detailsList.add(servingSize)
        detailsList.add(foodID)
        detailsList.add(quantity.toString())
        detailsList.add(meal)





        closeButton.setOnClickListener {
            switchToMyFoodPage()
        }

        editButton.setOnClickListener {
            val intent = Intent(this, EditDailyFoodActivity::class.java)
            intent.putStringArrayListExtra("foodDetailsList", detailsList)
            startActivity(intent)
        }
    }

    private fun switchToMyFoodPage(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

}