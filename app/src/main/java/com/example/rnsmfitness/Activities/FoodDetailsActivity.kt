package com.example.rnsmfitness.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Entities.FoodItem

import com.example.rnsmfitness.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FoodDetailsActivity : AppCompatActivity() {

    lateinit var detailsCalories: TextView
    lateinit var detailsProtein: TextView
    lateinit var detailsCarbs: TextView
    lateinit var detailsFat: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_item_details)

        detailsCalories = findViewById(R.id.food_detail_calories)
        detailsProtein = findViewById(R.id.food_detail_protein)
        detailsCarbs = findViewById(R.id.food_detail_carbs)
        detailsFat = findViewById(R.id.food_detail_fats)

        val protein: Int = intent.getIntegerArrayListExtra("valueList")!![0]
        val fat: Int = intent.getIntegerArrayListExtra("valueList")!![1]
        val carbs: Int = intent.getIntegerArrayListExtra("valueList")!![2]
        val calories: Int = (protein * 4) + (carbs * 4) + (fat * 9)

        detailsProtein.text = protein.toString()
        detailsCarbs.text = carbs.toString()
        detailsFat.text = fat.toString()
        detailsCalories.text = calories.toString()






        /*
        val proteinSegment = Segment("Protein", valueList.get(0))
        val carbSegment = Segment("Carb", valueList.get(1))
        val fatSegment = Segment("Fat", valueList.get(2))

        val pf = SegmentFormatter(Color.BLUE)
        val cf = SegmentFormatter(Color.BLUE)
        val ff = SegmentFormatter(Color.BLUE)

        FoodDetailsActivity.addSegment(proteinSegment, pf)
        FoodDetailsActivity.addSegment(carbSegment, cf)
        FoodDetailsActivity.addSegment(fatSegment, ff)
         */

    }



}