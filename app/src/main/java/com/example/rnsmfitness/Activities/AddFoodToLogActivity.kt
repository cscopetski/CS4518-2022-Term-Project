package com.example.rnsmfitness.Activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.rnsmfitness.Entities.DailyLogDataSource
import com.example.rnsmfitness.Entities.FoodItemBody
import com.example.rnsmfitness.R
import com.example.rnsmfitness.RetroFitClient
import com.example.rnsmfitness.services.DailyFoodBody
import com.example.rnsmfitness.services.MyFoodDailyFood
import com.example.rnsmfitness.services.USDADailyFood
import com.google.android.material.textfield.TextInputEditText
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.util.*

private const val TAG2 = "AddFoodToLogActivity"
const val INTENT_CODE = "ACTIVITY_NAME"
const val INTENT_CODE_USDA = "USDA"
const val INTENT_CODE_MY_FOOD = "MY_FOOD"

class AddFoodToLogActivity : AppCompatActivity() {
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
    private lateinit var editDate: Button
    private lateinit var date: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        date = Date(intent.getLongExtra("Date",System.currentTimeMillis()))

        setContentView(R.layout.activity_add_food_to_log)
        editDate = findViewById(R.id.edit_date)
        editDate.setText(date.toString())
        editDate.setOnClickListener {
            val c = Calendar.getInstance()
            c.time = date
            val year: Int = c.get(Calendar.YEAR)
            val month: Int = c.get(Calendar.MONTH)
            val day: Int = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->

                c.set(year, monthOfYear, dayOfMonth)
                date = Date(c.timeInMillis)
                editDate.setText(date.toString())
            }, year, month, day)

            dpd.show()

        }

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
        val quantity: Double = 1.0;

        var meal = "breakfast"

        if(!intent.getStringExtra("MEAL").isNullOrEmpty()){
            meal = intent.getStringExtra("MEAL").toString()
        }





        val protein: String = intent.getStringArrayListExtra("foodDetailsList")!![0]
        val fat: String = intent.getStringArrayListExtra("foodDetailsList")!![1]
        val carbs: String = intent.getStringArrayListExtra("foodDetailsList")!![2]
        val calories: Int = (protein.toInt() * 4) + (carbs.toInt() * 4) + (fat.toInt() * 9)
        val name: String = intent.getStringArrayListExtra("foodDetailsList")!![3]
        val servingSize: String = intent.getStringArrayListExtra("foodDetailsList")!![4].toString()

        editFoodName.setText(name)
        editFoodProtein.setText(protein)
        editFoodFat.setText(fat)
        editFoodCarbs.setText(carbs)
        editFoodServingSize.setText(servingSize.toString())
        editFoodQuantity.setText(quantity.toString())

        editFoodMeal.setSelection(spinnerAdapter.getPosition(meal.replaceFirstChar { it.uppercase() }))

        submitButton.setOnClickListener {

            if (editFoodQuantity.text.isNullOrEmpty()) {
                Log.d(TAG2, "There was a null value")
                submitButton.error = "Must fill out all fields"
            } else {
                Log.d(TAG2, "There were no null values, now sending intent")
                if(intent.getStringExtra(INTENT_CODE).equals(INTENT_CODE_MY_FOOD)){

                    val foodID: Int = intent.getStringArrayListExtra("foodDetailsList")!![5].toInt()
                    val body = MyFoodDailyFood(date, foodID, editFoodQuantity.text.toString().toDouble(), editFoodMeal.selectedItem.toString())
                    addFoodtoLog(body)


                }else{
                    val body = USDADailyFood(date, editFoodQuantity.text.toString().toDouble(), editFoodMeal.selectedItem.toString(), FoodItemBody(editFoodName.text.toString(), editFoodProtein.text.toString().toInt(), editFoodFat.text.toString().toInt(), editFoodCarbs.text.toString().toInt(), editFoodCalories.text.toString().toInt(), servingSize.toDouble()))
                    addUSDAFoodtoLog(body)
                }
            }
            finish()
        }

        cancelButton.setOnClickListener {
            finish()
        }



    }

    private fun addUSDAFoodtoLog(food: USDADailyFood){

        Log.v(TAG2, food.toString())
        val call: Call<ResponseBody> =
            RetroFitClient.dailyFoodLogService.insertUSDADailyFoodLogItem(food)

        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.v(TAG2, "in Delete on Response")
                if(response.isSuccessful){
                    Log.v(TAG2, "success")

                }else{
                    Log.v(TAG2, "fail")
                    Log.v(TAG2, response.code().toString())

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.v(TAG2, "big fail")
            }

        })
    }

        private fun addFoodtoLog(food: MyFoodDailyFood){

        Log.v(TAG2, food.toString())
        val call: Call<ResponseBody> =
            RetroFitClient.dailyFoodLogService.insertDailyFoodLogItem(food)

        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.v(TAG2, "in Delete on Response")
                if(response.isSuccessful){
                    Log.v(TAG2, "success")

                }else{
                    Log.v(TAG2, "fail")
                    Log.v(TAG2, response.code().toString())

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.v(TAG2, "big fail")
            }
        })
    }

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
            var quantity = 0.0

            if(!s.isNullOrEmpty()){
                quantity = s.toString().toDouble()
            }

            editFoodProtein.setText(Math.round(proteinBase.toInt()*quantity).toString())
            editFoodCarbs.setText(Math.round(carbsBase.toInt()*quantity).toString())
            editFoodFat.setText(Math.round(fatBase.toInt()*quantity).toString())

            editFoodCalories.setText((Math.round(proteinBase.toInt()*quantity * 4)  + Math.round(carbsBase.toInt()*quantity * 4) +  Math.round(fatBase.toInt() * quantity * 9)).toString())

            editFoodServingSize.setText(Math.round(servingSizeBase.toDouble()*quantity).toString())
        }

    }

}