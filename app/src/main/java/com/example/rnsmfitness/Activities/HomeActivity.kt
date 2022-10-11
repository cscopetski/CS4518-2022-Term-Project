package com.example.rnsmfitness.Activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Entities.*
import com.example.rnsmfitness.R
import com.example.rnsmfitness.RetroFitClient
import com.example.rnsmfitness.myFood.DailyFoodItemAdapter
import com.example.rnsmfitness.services.DailyFoodId
import com.example.rnsmfitness.services.DailyLog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import okhttp3.ResponseBody
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.util.*


private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity() {

    private lateinit var editDate: Button
    private var date: Date = Date(System.currentTimeMillis())

    private lateinit var breakfastRecyclerView: RecyclerView
    private lateinit var lunchRecyclerView: RecyclerView
    private lateinit var dinnerRecyclerView: RecyclerView
    private lateinit var snackRecyclerView: RecyclerView

    private lateinit var breakfastAdapter: DailyFoodItemAdapter
    private lateinit var lunchAdapter: DailyFoodItemAdapter
    private lateinit var dinnerAdapter: DailyFoodItemAdapter
    private lateinit var snackAdapter: DailyFoodItemAdapter
    private lateinit var dataSource: DailyFoodLogDataSource

    private lateinit var breakfastCardView: CardView
    private lateinit var breakfastView: LinearLayout
    private lateinit var lunchCardView: CardView
    private lateinit var lunchView: LinearLayout
    private lateinit var dinnerCardView: CardView
    private lateinit var dinnerView: LinearLayout
    private lateinit var snackCardView: CardView
    private lateinit var chartsCard: CardView
    private lateinit var chartView: LinearLayout
    private lateinit var snackView: LinearLayout
    private lateinit var liveList: LiveData<List<DailyFoodItem>>
    private lateinit var caloriePie: PieChart
    private lateinit var proteinPie: PieChart
    private lateinit var carbPie: PieChart
    private lateinit var fatPie: PieChart
    private lateinit var dailyLog: LiveData<DailyLog>
    private lateinit var breakfastCals: TextView
    private lateinit var lunchCals: TextView
    private lateinit var dinnerCals: TextView
    private lateinit var snackCals: TextView
    private lateinit var navBar: BottomNavigationView

    var data: Intent? = null

    companion object {
        const val RESULT = "RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_new)
        navBar = findViewById(R.id.bottom_nav)
        navBar.selectedItemId = (R.id.home)

        navBar.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.foods -> startActivity(Intent(this, MyFoodList::class.java))
                R.id.settings -> startActivity(Intent(this, SettingsActivity::class.java))
            }
            overridePendingTransition(0, 0);
            true
        })

        editDate = findViewById(R.id.edit_date)

        breakfastCardView = findViewById(R.id.breakfast_card)
        breakfastView = findViewById(R.id.breakfast_view)

        lunchCardView = findViewById(R.id.lunch_card)
        lunchView = findViewById(R.id.lunch_view)


        dinnerCardView = findViewById(R.id.dinner_card)
        dinnerView = findViewById(R.id.dinner_view)

        snackCardView = findViewById(R.id.snack_card)
        snackView = findViewById(R.id.snack_view)

        chartsCard = findViewById(R.id.charts_card)
        chartView = findViewById(R.id.chart_view)

        caloriePie = findViewById(R.id.caloriePie)
        proteinPie = findViewById(R.id.proteinPie)
        carbPie = findViewById(R.id.carbPie)
        fatPie = findViewById(R.id.fatPie)

        breakfastCals = findViewById(R.id.breakfast_cals)
        lunchCals = findViewById(R.id.lunch_cals)
        dinnerCals = findViewById(R.id.dinner_cals)
        snackCals = findViewById(R.id.snack_cals)



        breakfastCardView.setOnClickListener {
            if (breakfastView.visibility == View.VISIBLE) {
                breakfastView.visibility = (View.GONE);
            } else {
                breakfastView.visibility = (View.VISIBLE);
            }
        }

        lunchCardView.setOnClickListener {
            if (lunchView.visibility == View.VISIBLE) {
                lunchView.visibility = (View.GONE);
            } else {
                lunchView.visibility = (View.VISIBLE);
            }
        }

        dinnerCardView.setOnClickListener {
            if (dinnerView.visibility == View.VISIBLE) {
                dinnerView.visibility = (View.GONE);
            } else {
                dinnerView.visibility = (View.VISIBLE);
            }
        }

        snackCardView.setOnClickListener {
            if (snackView.visibility == View.VISIBLE) {
                snackView.visibility = (View.GONE);
            } else {
                snackView.visibility = (View.VISIBLE);
            }
        }

        chartsCard.setOnClickListener {
            if (chartView.visibility == View.VISIBLE) {
                chartView.visibility = (View.GONE);
            } else {
                chartView.visibility = (View.VISIBLE);
            }
        }

        editDate.setText(date.toString())
        editDate.setOnClickListener {
            val c = Calendar.getInstance()
            c.time = date
            val year: Int = c.get(Calendar.YEAR)
            val month: Int = c.get(Calendar.MONTH)
            val day: Int = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
//                val dateString = "$monthOfYear/$dayOfMonth/$year"
                c.set(year, monthOfYear, dayOfMonth)
                date = Date(c.timeInMillis)
                editDate.setText(date.toString())
                dataSource.getDBFoods(date)
                DailyLogDataSource.getDataSource().getDailyDetails(date)
            }, year, month, day)

            dpd.show()

        }

        breakfastRecyclerView = findViewById(R.id.breakfast_recycler)
        lunchRecyclerView = findViewById(R.id.lunch_recycler)
        dinnerRecyclerView = findViewById(R.id.dinner_recycler)
        snackRecyclerView = findViewById(R.id.snack_recycler)

        breakfastRecyclerView.layoutManager = LinearLayoutManager(this)
        lunchRecyclerView.layoutManager = LinearLayoutManager(this)
        dinnerRecyclerView.layoutManager = LinearLayoutManager(this)
        snackRecyclerView.layoutManager = LinearLayoutManager(this)

        dataSource = DailyFoodLogDataSource.getDataSource()
        dataSource.getDBFoods(date)

        liveList = dataSource.getFoodList()

        liveList.observe(this)
        { it ->
            it?.let { list ->
                breakfastAdapter =
                    DailyFoodItemAdapter(this, list.filter { it.meal.equals("breakfast") })
                breakfastRecyclerView.adapter = breakfastAdapter


                lunchAdapter = DailyFoodItemAdapter(this, list.filter { it.meal.equals("lunch") })
                lunchRecyclerView.adapter = lunchAdapter

                dinnerAdapter = DailyFoodItemAdapter(this, list.filter { it.meal.equals("dinner") })
                dinnerRecyclerView.adapter = dinnerAdapter

                snackAdapter = DailyFoodItemAdapter(this, list.filter { it.meal.equals("snacks") })
                snackRecyclerView.adapter = snackAdapter
            }
            setCalTotals()
        }

        DailyLogDataSource.getDataSource().getDailyDetails(date)
        dailyLog = DailyLogDataSource.getDataSource().getDailyLog()

        dailyLog.observe(this){ it ->
            setPies(it)
        }

        setRecyclerViewItemTouchListener()

    }

    override fun onResume() {
        super.onResume()
        dataSource.getDBFoods(date)
        liveList = dataSource.getFoodList()
        navBar.selectedItemId = (R.id.home)
    }

    private fun setCalTotals(){
        breakfastCals.text = DailyFoodLogDataSource.getDataSource().getMealCalories("breakfast").toString()
        lunchCals.text = DailyFoodLogDataSource.getDataSource().getMealCalories("lunch").toString()
        dinnerCals.text = DailyFoodLogDataSource.getDataSource().getMealCalories("dinner").toString()
        snackCals.text = DailyFoodLogDataSource.getDataSource().getMealCalories("snacks").toString()
    }

    private fun setRecyclerViewItemTouchListener() {

        val breakfastItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val curFood: DailyFoodItem? =
                    dataSource.getFoodList().value?.filter { it.meal.equals("breakfast") }?.get(viewHolder.bindingAdapterPosition)
                if (curFood != null){
                    Log.v(TAG, curFood.toString())
                    deleteFood(curFood.id)
                }

            }
        }
        val lunchItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val curFood: DailyFoodItem? =
                    dataSource.getFoodList().value?.filter { it.meal.equals("lunch") }?.get(viewHolder.bindingAdapterPosition)
                if (curFood != null){
                    Log.v(TAG, curFood.toString())
                    deleteFood(curFood.id)
                }

            }
        }
        val dinnerItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val curFood: DailyFoodItem? =
                    dataSource.getFoodList().value?.filter { it.meal.equals("dinner") }?.get(viewHolder.bindingAdapterPosition)
                if (curFood != null){
                    Log.v(TAG, curFood.toString())
                    deleteFood(curFood.id)
                }

            }
        }

        val snackItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val curFood: DailyFoodItem? =
                    dataSource.getFoodList().value?.filter { it.meal.equals("snacks") }?.get(viewHolder.bindingAdapterPosition)
                if (curFood != null){
                    Log.v(TAG, curFood.toString())
                    deleteFood(curFood.id)
                }

            }
        }

        ItemTouchHelper(breakfastItemTouchCallback).attachToRecyclerView(breakfastRecyclerView)
        ItemTouchHelper(lunchItemTouchCallback).attachToRecyclerView(lunchRecyclerView)
        ItemTouchHelper(dinnerItemTouchCallback).attachToRecyclerView(dinnerRecyclerView)
        ItemTouchHelper(snackItemTouchCallback).attachToRecyclerView(snackRecyclerView)

    }

    private fun deleteFood(id: Int){
        Log.v(TAG, id.toString())

        val call: Call<ResponseBody> =
            RetroFitClient.dailyFoodLogService.deleteDailyFoodLogItem(DailyFoodId(id))

        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.v(TAG, "in Delete on Response")
                if(response.isSuccessful){
                    Log.v(TAG, "in if")
                    DailyLogDataSource.getDataSource().getDailyDetails(date);
                    dataSource.getDBFoods(date);
                }else{
                    Log.v(TAG, "in else")
                    Log.v(TAG, response.code().toString())

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.v(TAG, "In delete on failure")
            }
        })
    }


    private fun setPies(log: DailyLog){

        Log.v(TAG, log.toString())

        caloriePie.clearChart()
        proteinPie.clearChart()
        carbPie.clearChart()
        fatPie.clearChart()

        var calsEaten = log.calorie_results
        var calsRemaining = log.calorie_goal - log.calorie_results
        if (calsRemaining < 0){calsRemaining = 0}

        var proteinEaten = log.protein_results
        var proteinRemaining = log.protein_goal - log.protein_results
        if (proteinRemaining < 0){proteinRemaining = 0}

        var carbsEaten = log.carb_results
        var carbsRemaining = log.carb_goal - log.carb_results
        if (carbsRemaining < 0){carbsRemaining = 0}

        var fatsEaten = log.fat_results
        var fatsRemaining = log.fat_goal - log.fat_results
        if (fatsRemaining < 0){fatsRemaining = 0}


        caloriePie.addPieSlice(
            PieModel(
                "Calories Eaten", calsEaten.toFloat(),
                Color.parseColor("#66BB6A") //green
            )
        )
        caloriePie.addPieSlice(
            PieModel(
                "Calories Left", calsRemaining.toFloat(),
                Color.parseColor("#EF5350") //red
            )
        )


        proteinPie.addPieSlice(
            PieModel(
                "Protein Eaten", proteinEaten.toFloat(),
                Color.parseColor("#66BB6A") //green
            )
        )
        proteinPie.addPieSlice(
            PieModel(
                "Protein Left", proteinRemaining.toFloat(),
                Color.parseColor("#EF5350") //red
            )
        )


        carbPie.addPieSlice(
            PieModel(
                "Carbs Eaten", carbsEaten.toFloat(),
                Color.parseColor("#66BB6A") //green
            )
        )
        carbPie.addPieSlice(
            PieModel(
                "Carbs Left", carbsRemaining.toFloat(),
                Color.parseColor("#EF5350") //red
            )
        )


        fatPie.addPieSlice(
            PieModel(
                "Fat Eaten", fatsEaten.toFloat(),
                Color.parseColor("#66BB6A") //green
            )
        )
        fatPie.addPieSlice(
            PieModel(
                "Fat Left", fatsRemaining.toFloat(),
                Color.parseColor("#EF5350") //red
            )
        )

        caloriePie.innerPadding = 60F;
        caloriePie.innerValueUnit = "cal"

        proteinPie.innerPadding = 60F;
        proteinPie.innerValueUnit = "g"

        carbPie.innerPadding = 60F;
        carbPie.innerValueUnit = "g"

        fatPie.innerPadding = 60F;
        fatPie.innerValueUnit = "g"
    }

}