package com.example.rnsmfitness.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.rnsmfitness.Entities.FoodItem
import com.example.rnsmfitness.Entities.FoodItemBody
import com.example.rnsmfitness.R
import com.example.rnsmfitness.RetroFitClient
import com.google.android.material.textfield.TextInputEditText
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG2 = "CreateFoodActivity"


class CreateFoodActivity: AppCompatActivity() {

    private lateinit var addFoodName: TextInputEditText
    private lateinit var addFoodProtein: TextInputEditText
    private lateinit var addFoodCarbs: TextInputEditText
    private lateinit var addFoodFat: TextInputEditText
    private lateinit var addFoodServingSize: TextInputEditText
    private lateinit var foodCalories: TextInputEditText
    private lateinit var createButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_food)

        addFoodName = findViewById(R.id.add_food_name)
        addFoodProtein = findViewById(R.id.add_food_protein)
        addFoodCarbs = findViewById(R.id.add_food_carbs)
        addFoodFat = findViewById(R.id.add_food_fat)
        addFoodServingSize = findViewById(R.id.add_food_serving_size)
        createButton = findViewById(R.id.done_button)
        foodCalories = findViewById(R.id.food_calories)

        addFoodProtein.addTextChangedListener(calorieWatcherP)
        addFoodCarbs.addTextChangedListener(calorieWatcherP)
        addFoodFat.addTextChangedListener(calorieWatcherP)

        createButton.setOnClickListener {

            if (addFoodName.text.isNullOrEmpty() || addFoodProtein.text.isNullOrEmpty() || addFoodCarbs.text.isNullOrEmpty() || addFoodFat.text.isNullOrEmpty() || addFoodServingSize.text.isNullOrEmpty()) {
                Log.d(TAG2, "There was a null value")
                setResult(Activity.RESULT_CANCELED, Intent())
            } else {
                Log.d(TAG2, "There were no null values, now sending intent")
                insertFood(FoodItemBody(addFoodName.text.toString(), addFoodProtein.text.toString().toInt(), addFoodCarbs.text.toString().toInt(), addFoodFat.text.toString().toInt(), foodCalories.text.toString().toInt(), addFoodServingSize.text.toString().toDouble()))
            }
            finish()
            switchToMyFoodPage()

        }
    }

    private fun insertFood(food: FoodItemBody){
        val call: Call<ResponseBody> =
            RetroFitClient.foodService.insertFood(food)


        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    setResult(Activity.RESULT_OK, Intent())
                    finish()
                }else{
                    setResult(Activity.RESULT_CANCELED, Intent())
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                setResult(Activity.RESULT_CANCELED, Intent())
                finish()
            }
        })
    }

    private fun switchToMyFoodPage(){
        val intent = Intent(this, MyFoodList::class.java)
        startActivity(intent)
        finish()
    }

    private val calorieWatcherP = object : TextWatcher{

        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val protein = addFoodProtein.text.toString();
            val carbs = addFoodCarbs.text.toString();
            val fat = addFoodFat.text.toString();

            foodCalories.setText(((if (protein.isNullOrEmpty()) 0 else protein.toInt()) * 4  + if (carbs.isNullOrEmpty()) 0 else carbs.toInt() * 4 + if (fat.isNullOrEmpty()) 0 else fat.toInt() * 9).toString());
        }

    }

}