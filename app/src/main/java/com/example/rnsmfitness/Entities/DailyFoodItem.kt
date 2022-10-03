package com.example.rnsmfitness.Entities

data class DailyFoodItem (val id: Int, val name: String, var is_visible: Int, val protein: Int, val fat: Int, val carbs: Int, val calories: Int, val serving_size: Double, val quantity: Double, val meal : String)