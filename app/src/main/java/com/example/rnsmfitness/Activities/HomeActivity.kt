package com.example.rnsmfitness.Activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Entities.*
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import com.example.rnsmfitness.Activities.ScanActivity.Companion.CAMERARESULT
import com.example.rnsmfitness.R
import com.example.rnsmfitness.RetroFitClient
import com.example.rnsmfitness.myFood.DailyFoodItemAdapter

import com.example.rnsmfitness.services.DailyFoodId
import com.example.rnsmfitness.services.DailyLog
import com.example.rnsmfitness.services.USDADailyFood
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.ResponseBody
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.parseInt

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
    private lateinit var snackView: LinearLayout
    private lateinit var liveList: LiveData<List<DailyFoodItem>>
    private lateinit var caloriePie: PieChart
    private lateinit var proteinPie: PieChart
    private lateinit var carbPie: PieChart
    private lateinit var fatPie: PieChart

    lateinit var test: FloatingActionButton
//    lateinit var logoutButton: Button
//    lateinit var nameTextView: TextView


//    lateinit var btnScan: Button
    lateinit var resultText: TextView

    lateinit var signUpButton: Button

    var data: Intent? = null

    companion object {
        const val RESULT = "RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_new)

        test = findViewById(R.id.test_button)

        test.setOnClickListener{
            val intent = Intent(this, MyFoodList::class.java)
            startActivity(intent)

        }

        editDate = findViewById(R.id.edit_date)

        breakfastCardView = findViewById(R.id.breakfast_card)
        breakfastView = findViewById(R.id.breakfast_view)

        lunchCardView = findViewById(R.id.lunch_card)
        lunchView = findViewById(R.id.lunch_view)
//        btnScan = findViewById(R.id.btnScan)
//        resultText = findViewById(R.id.resultText)


//        nameTextView.text = intent.getStringExtra("Name")

        dinnerCardView = findViewById(R.id.dinner_card)
        dinnerView = findViewById(R.id.dinner_view)

        snackCardView = findViewById(R.id.snack_card)
        snackView = findViewById(R.id.snack_view)

        caloriePie = findViewById(R.id.caloriePie)
        proteinPie = findViewById(R.id.proteinPie)
        carbPie = findViewById(R.id.carbPie)
        fatPie = findViewById(R.id.fatPie)

        breakfastCardView.setOnClickListener {
            if (breakfastView.visibility == View.VISIBLE) {
//                TransitionManager.beginDelayedTransition(breakfastCardView, AutoTransition());
                breakfastView.visibility = (View.GONE);
//                arrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
            } else {
//                TransitionManager.beginDelayedTransition(breakfastCardView, AutoTransition());
                breakfastView.visibility = (View.VISIBLE);
//                arrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
            }
        }

        lunchCardView.setOnClickListener {
            if (lunchView.visibility == View.VISIBLE) {
                lunchView.visibility = (View.GONE);
            } else {
//                TransitionManager.beginDelayedTransition(lunchCardView, AutoTransition());
                lunchView.visibility = (View.VISIBLE);
            }
        }

        dinnerCardView.setOnClickListener {
            if (dinnerView.visibility == View.VISIBLE) {
                dinnerView.visibility = (View.GONE);
            } else {
//                TransitionManager.beginDelayedTransition(dinnerCardView, AutoTransition());
                dinnerView.visibility = (View.VISIBLE);
            }
        }

        snackCardView.setOnClickListener {
            if (snackView.visibility == View.VISIBLE) {
                snackView.visibility = (View.GONE);
            } else {
//                TransitionManager.beginDelayedTransition(snackCardView, AutoTransition());
                snackView.visibility = (View.VISIBLE);
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
            }, year, month, day)

            dpd.show()
        }


        breakfastRecyclerView = findViewById(R.id.breakfast_recycler)
        lunchRecyclerView = findViewById(R.id.lunch_recycler)
        dinnerRecyclerView = findViewById(R.id.dinner_recycler)
        snackRecyclerView = findViewById(R.id.snack_recycler)


//        btnScan.setOnClickListener {
//            /*val intent = Intent(applicationContext, ScanActivity::class.java)
//            startActivity(intent)*/
//            openCameraActivityForResult()
//        }

//        val result = data?.getStringExtra(RESULT)
//
//        if(result != null)
//            resultText.text = result.toString()






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
        }


        setRecyclerViewItemTouchListener()

        caloriePie.addPieSlice(
            PieModel(
                "Calories Eaten", 2400F,
                Color.parseColor("#66BB6A") //green
            )
        )
        caloriePie.addPieSlice(
            PieModel(
                "Calories Left", 500F,
                Color.parseColor("#EF5350") //red
            )
        )


        proteinPie.addPieSlice(
            PieModel(
                "Protein Eaten", 180F,
                Color.parseColor("#66BB6A") //green
            )
        )
        proteinPie.addPieSlice(
            PieModel(
                "Protein Left", 40F,
                Color.parseColor("#EF5350") //red
            )
        )


        carbPie.addPieSlice(
            PieModel(
                "Carbs Eaten", 300F,
                Color.parseColor("#66BB6A") //green
            )
        )
        carbPie.addPieSlice(
            PieModel(
                "Carbs Left", 300F,
                Color.parseColor("#EF5350") //red
            )
        )


        fatPie.addPieSlice(
            PieModel(
                "Fat Eaten", 20F,
                Color.parseColor("#66BB6A") //green
            )
        )
        fatPie.addPieSlice(
            PieModel(
                "Fat Left", 80F,
                Color.parseColor("#EF5350") //red
            )
        )

        caloriePie.innerPadding = 60F;
        caloriePie.startAnimation()

        proteinPie.innerPadding = 60F;
        proteinPie.startAnimation()

        carbPie.innerPadding = 60F;
        carbPie.startAnimation()

        fatPie.innerPadding = 60F;
        fatPie.startAnimation()


    }

    override fun onResume() {
        super.onResume()
        dataSource.getDBFoods(date)
        liveList = dataSource.getFoodList()
    }

    private fun setRecyclerViewItemTouchListener() {


        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val curFood: DailyFoodItem? =  dataSource.getFoodList().value?.get(viewHolder.bindingAdapterPosition)
                if (curFood != null){
                    Log.v(TAG, curFood.toString())
                    deleteFood(curFood.id)
                }

            }
        }

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(breakfastRecyclerView)
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(lunchRecyclerView)
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(dinnerRecyclerView)
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(snackRecyclerView)

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
    private fun testInsert(){
        //TODO("Charlie, this is the example of using the daily log, if you click the black fab on the daily food log page it will print the response to console")
        val call: Call<DailyLog> =
            RetroFitClient.dailyLogService.getDailyLog(date)

        call.enqueue(object : Callback<DailyLog> {
            override fun onResponse(call: Call<DailyLog>, response: Response<DailyLog>) {
                if(response.isSuccessful){
                    Log.v(TAG, response.body().toString())
                    //TODO find way to return response.body()


                }else{
                    Log.v(TAG, response.code().toString())

                }
            }

            override fun onFailure(call: Call<DailyLog>, t: Throwable) {
                Log.v(TAG, "big fail")
            }
        })
    }

//    private fun openCameraActivityForResult() {
//        val intent = Intent(this, ScanActivity::class.java)
//        resultLauncher.launch(intent)
//    }
//
//    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == CAMERARESULT) {
//            // There are no request codes
//            data = result.data
//            val result = data?.getStringExtra(RESULT)
//
//            if(result != null)
//                resultText.text = result.toString()
//        }
//    }



}