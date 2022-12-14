package com.example.rnsmfitness.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.rnsmfitness.R
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.sql.Date


private const val TAG = "FoodDetailsActivity"

class FoodDetailsActivity() : AppCompatActivity() {

    lateinit var detailsCalories: TextView
    lateinit var detailsProtein: TextView
    lateinit var detailsCarbs: TextView
    lateinit var detailsFat: TextView
    lateinit var detailsName: TextView
    lateinit var closeButton: Button
    private lateinit var editButton: Button
    private lateinit var addToLogButton: Button
    private lateinit var detailsPieChart: PieChart

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
        addToLogButton = findViewById(R.id.add_food_to_log_button)
        detailsPieChart = findViewById(R.id.pieChartFoodDetails)

        val protein: String = intent.getStringArrayListExtra("valueList")!![0]
        val fat: String = intent.getStringArrayListExtra("valueList")!![1]
        val carbs: String = intent.getStringArrayListExtra("valueList")!![2]
        val calories: Int = (protein.toInt() * 4) + (carbs.toInt() * 4) + (fat.toInt() * 9)
        val name: String = intent.getStringArrayListExtra("valueList")!![3]
        val servingSize: String = intent.getStringArrayListExtra("valueList")!![4].toString()
        val foodID: String = intent.getStringArrayListExtra("valueList")!![5]
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
        detailsList.add(foodID)

        closeButton.setOnClickListener {
            switchToMyFoodPage()
        }

        editButton.setOnClickListener {
            val intent = Intent(this, EditFoodActivity::class.java)
            intent.putStringArrayListExtra("foodDetailsList", detailsList)
            startActivity(intent)
            finish()
        }

        addToLogButton.setOnClickListener{
            val intent = Intent(this, AddFoodToLogActivity::class.java)
            intent.putStringArrayListExtra("foodDetailsList", detailsList)
            intent.putExtra("Date",date.time)
            intent.putExtra(INTENT_CODE, INTENT_CODE_MY_FOOD)
            startActivity(intent)
            finish()
        }

        val total: Float = protein.toFloat() + carbs.toFloat() + fat.toFloat()

        detailsPieChart.addPieSlice(
            PieModel(
                "Protein", ((protein.toFloat() / total)) * 100F,
                Color.parseColor("#66BB6A") //green
            )
        )
        detailsPieChart.addPieSlice(
            PieModel(
                "Carbs", (carbs.toFloat() / total) * 100F,
                Color.parseColor("#EF5350") //red
            )
        )

        detailsPieChart.addPieSlice(
            PieModel(
                "Fat", (fat.toFloat() / total) * 100F,
                Color.parseColor("#29B6F6")//light blue
            )
        )

        detailsPieChart.innerPadding = 60F;
        detailsPieChart.innerValueUnit = "%"

    }

    private fun switchToMyFoodPage(){
        val intent = Intent(this, MyFoodList::class.java)
        startActivity(intent)
        finish()
    }

}