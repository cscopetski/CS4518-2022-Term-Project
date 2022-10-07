package com.example.rnsmfitness.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.rnsmfitness.Entities.FoodItem
import com.example.rnsmfitness.Entities.FoodItemBody
import com.example.rnsmfitness.R
import com.example.rnsmfitness.RetroFitClient
import com.example.rnsmfitness.services.DailyFoodBody
import com.google.android.material.textfield.TextInputEditText
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG2 = "EditDailyFoodActivity"

class EditDailyFoodActivity : AppCompatActivity() {

    private lateinit var editFoodQuantity: TextInputEditText
    private lateinit var editFoodMeal: Spinner
    private lateinit var editFoodName: TextInputEditText
    private lateinit var editFoodProtein: TextInputEditText
    private lateinit var editFoodCarbs: TextInputEditText
    private lateinit var editFoodFat: TextInputEditText
    private lateinit var editFoodServingSize: TextInputEditText
    private lateinit var editFoodCalories: TextInputEditText
    private lateinit var submitButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_daily_food)

        editFoodName = findViewById(R.id.edit_food_name)
        editFoodProtein = findViewById(R.id.edit_food_protein)
        editFoodCarbs = findViewById(R.id.edit_food_carbs)
        editFoodFat = findViewById(R.id.edit_food_fat)
        editFoodServingSize = findViewById(R.id.edit_food_serving_size)
        submitButton = findViewById(R.id.submit_edits_button)
        editFoodCalories = findViewById(R.id.edit_food_calories)
        cancelButton = findViewById(R.id.cancel_changes_button)
        editFoodQuantity = findViewById(R.id.edit_food_quantity)
        editFoodMeal = findViewById(R.id.edit_food_meal)

        editFoodQuantity.addTextChangedListener(quantityWatcher)

        val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.meals_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            editFoodMeal.adapter = adapter
        }

        val protein: String = intent.getStringArrayListExtra("foodDetailsList")!![0]
        val fat: String = intent.getStringArrayListExtra("foodDetailsList")!![1]
        val carbs: String = intent.getStringArrayListExtra("foodDetailsList")!![2]
        val name: String = intent.getStringArrayListExtra("foodDetailsList")!![3]
        val servingSize: Double = intent.getStringArrayListExtra("foodDetailsList")!![4].toDouble()
        val foodID: Int = intent.getStringArrayListExtra("foodDetailsList")!![5].toInt()
        val quantity: Double = intent.getStringArrayListExtra("foodDetailsList")!![6].toDouble()
        val meal: String = intent.getStringArrayListExtra("foodDetailsList")!![7]


        editFoodName.setText(name)
        editFoodProtein.setText(protein)
        editFoodFat.setText(fat)
        editFoodCarbs.setText(carbs)
        editFoodServingSize.setText(servingSize.toString())
        editFoodQuantity.setText(quantity.toString())

        editFoodMeal.setSelection(spinnerAdapter.getPosition(meal.replaceFirstChar { it.uppercase() }))

        cancelButton.setOnClickListener {
            finish()
        }


        submitButton.setOnClickListener {

            if (editFoodName.text.isNullOrEmpty() || editFoodProtein.text.isNullOrEmpty() || editFoodCarbs.text.isNullOrEmpty() || editFoodFat.text.isNullOrEmpty() || editFoodServingSize.text.isNullOrEmpty()) {
                Log.d(TAG2, "There was a null value")
                setResult(Activity.RESULT_CANCELED, Intent())
            } else {
                Log.d(TAG2, "There were no null values, now sending intent")
                updateDailyFood(DailyFoodBody(foodID, editFoodQuantity.text.toString().toDouble(), editFoodMeal.selectedItem.toString()))
            }
            finish()
        }
    }

    private fun updateDailyFood(food: DailyFoodBody){
        val call: Call<ResponseBody> =
            RetroFitClient.dailyFoodLogService.updateDailyFoodLogItem(food)


        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    setResult(Activity.RESULT_OK, Intent())
                    Log.d(TAG2,"On Response IF")
                    finish()
                }else{
                    setResult(Activity.RESULT_CANCELED, Intent())
                    Log.d(TAG2,"On Response else")
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                setResult(Activity.RESULT_CANCELED, Intent())
                Log.d(TAG2,"On test")
                finish()
            }
        })
    }

//    private fun switchToMyFoodPage(){
//        val intent = Intent(this, MyFoodList::class.java)
//        startActivity(intent)
//    }

    private val quantityWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            val proteinBase: String = intent.getStringArrayListExtra("foodDetailsList")!![0]
            val fatBase: String = intent.getStringArrayListExtra("foodDetailsList")!![1]
            val carbsBase: String = intent.getStringArrayListExtra("foodDetailsList")!![2]
            val servingSizeBase: Double = intent.getStringArrayListExtra("foodDetailsList")!![4].toDouble()
            val quantity = s.toString().toDouble()

            editFoodProtein.setText(Math.round(proteinBase.toInt()*quantity).toString())
            editFoodCarbs.setText(Math.round(carbsBase.toInt()*quantity).toString())
            editFoodFat.setText(Math.round(fatBase.toInt()*quantity).toString())

            val protein = editFoodProtein.text.toString();
            val carbs = editFoodCarbs.text.toString();
            val fat = editFoodFat.text.toString();

            editFoodCalories.setText(((if (protein.isNullOrEmpty()) 0 else protein.toInt()) * 4  + if (carbs.isNullOrEmpty()) 0 else carbs.toInt() * 4 + if (fat.isNullOrEmpty()) 0 else fat.toInt() * 9).toString());

            editFoodServingSize.setText(Math.round(servingSizeBase.toDouble()*quantity).toString())
        }

    }

}