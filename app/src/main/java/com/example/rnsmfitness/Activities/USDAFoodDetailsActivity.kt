package com.example.rnsmfitness.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Entities.FoodItem
import com.example.rnsmfitness.Entities.FoodItemBody

import com.example.rnsmfitness.R
import com.example.rnsmfitness.RetroFitClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.ResponseBody
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date

class USDAFoodDetailsActivity() : AppCompatActivity() {

    lateinit var detailsCalories: TextView
    lateinit var detailsProtein: TextView
    lateinit var detailsCarbs: TextView
    lateinit var detailsFat: TextView
    lateinit var detailsName: TextView
    lateinit var closeButton: Button
    lateinit var addToMyFoodsButton: Button
    lateinit var addToLogButton: Button
    lateinit var usdaPieChart: PieChart

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usda_food_details)



        detailsCalories = findViewById(R.id.usda_food_detail_calories)
        detailsProtein = findViewById(R.id.usda_food_detail_protein)
        detailsCarbs = findViewById(R.id.usda_food_detail_carbs)
        detailsFat = findViewById(R.id.usda_food_detail_fats)
        detailsName = findViewById(R.id.usda_food_detail_name)
        closeButton = findViewById(R.id.usda_close_button)
        addToMyFoodsButton = findViewById(R.id.add_to_my_foods_button)
        addToLogButton = findViewById(R.id.add_food_to_log_button)
        usdaPieChart = findViewById(R.id.pieChartUSDADetails)

        val protein: String = intent.getStringArrayListExtra("valueList")!![0]
        val fat: String = intent.getStringArrayListExtra("valueList")!![1]
        val carbs: String = intent.getStringArrayListExtra("valueList")!![2]
        val calories: Int = (protein.toInt() * 4) + (carbs.toInt() * 4) + (fat.toInt() * 9)
        val name: String = intent.getStringArrayListExtra("valueList")!![3]
        val servingSize: String = intent.getStringArrayListExtra("valueList")!![4].toString()
        val date: Date = Date(intent.getLongExtra("Date",System.currentTimeMillis()))
        detailsProtein.text = protein + "g"
        detailsCarbs.text = carbs + "g"
        detailsFat.text = fat + "g"
        detailsCalories.text = calories.toString() + " cal"
        detailsName.text = name

        val detailsList = ArrayList<String>()
        detailsList.add(protein)
        detailsList.add(fat)
        detailsList.add(carbs)
        detailsList.add(name)
        detailsList.add(servingSize)


        closeButton.setOnClickListener {
            finish()
        }

        addToMyFoodsButton.setOnClickListener {
            insertFood(FoodItemBody(name, protein.toInt(), fat.toInt(), carbs.toInt(), calories, servingSize.toDouble()))
            Toast.makeText(this, "Food has been added to MyFoods list", Toast.LENGTH_SHORT).show()
        }

        addToLogButton.setOnClickListener{
            val intent = Intent(this, AddFoodToLogActivity::class.java)
            intent.putStringArrayListExtra("foodDetailsList", detailsList)
            intent.putExtra("Date",date.time)
            intent.putExtra(INTENT_CODE, INTENT_CODE_USDA)
            startActivity(intent)
            finish()
        }
        val total: Float = protein.toFloat() + carbs.toFloat() + fat.toFloat()

        usdaPieChart.addPieSlice(
            PieModel(
                "Protein", ((protein.toFloat() / total)) * 100F,
                Color.parseColor("#66BB6A") //green
            )
        )
        usdaPieChart.addPieSlice(
            PieModel(
                "Carbs", (carbs.toFloat() / total) * 100F,
                Color.parseColor("#EF5350") //red
            )
        )

        usdaPieChart.addPieSlice(
            PieModel(
                "Fat", (fat.toFloat() / total) * 100F,
                Color.parseColor("#29B6F6")//light blue
            )
        )

        usdaPieChart.innerPadding = 60F;
        usdaPieChart.innerValueUnit = "%"


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








}