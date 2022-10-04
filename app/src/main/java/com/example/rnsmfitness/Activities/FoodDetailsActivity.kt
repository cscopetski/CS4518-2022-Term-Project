package com.example.rnsmfitness.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Entities.FoodItem

import com.example.rnsmfitness.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FoodDetailsActivity() : AppCompatActivity() {

    lateinit var detailsCalories: TextView
    lateinit var detailsProtein: TextView
    lateinit var detailsCarbs: TextView
    lateinit var detailsFat: TextView
    lateinit var detailsName: TextView
    lateinit var closeButton: Button
    lateinit var editButton: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_item_details)

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
        val calories: Int = (protein.toInt() * 4) + (carbs.toInt() * 4) + (fat.toInt() * 9)
        val name: String = intent.getStringArrayListExtra("valueList")!![3]
        val servingSize: String = intent.getStringArrayListExtra("valueList")!![4].toString()
        val foodID: String = intent.getStringArrayListExtra("valueList")!![5]

        detailsProtein.text = protein + "g"
        detailsCarbs.text = carbs + "g"
        detailsFat.text = fat + "g"
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





        closeButton.setOnClickListener {
            switchToMyFoodPage()
        }

        editButton.setOnClickListener {
            val intent = Intent(this, EditFoodActivity::class.java)
            intent.putStringArrayListExtra("foodDetailsList", detailsList)
            startActivity(intent)
        }
    }

    private fun switchToMyFoodPage(){
        val intent = Intent(this, MyFoodList::class.java)
        startActivity(intent)
    }





}