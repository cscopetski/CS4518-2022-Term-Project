package com.example.rnsmfitness.Activities

import android.app.DatePickerDialog
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Entities.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.rnsmfitness.Activities.ScanActivity.Companion.CAMERARESULT
import com.example.rnsmfitness.Entities.LoginCredentials
import com.example.rnsmfitness.Entities.USDAFoodItem
import com.example.rnsmfitness.Entities.User
import com.example.rnsmfitness.R
import com.example.rnsmfitness.RetroFitClient
import com.example.rnsmfitness.myFood.DailyFoodItemAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.parseInt
import java.sql.Date
import java.time.Year
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

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


    lateinit var myFoodButton: Button
    lateinit var logoutButton: Button
    lateinit var nameTextView: TextView


    lateinit var btnScan: Button
    lateinit var resultText: TextView

    lateinit var signUpButton:Button

    var data: Intent? = null

    companion object{
        const val RESULT = "RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_new)

        editDate = findViewById(R.id.edit_date)

        breakfastCardView = findViewById(R.id.breakfast_card)
        breakfastView = findViewById(R.id.breakfast_view)

        lunchCardView = findViewById(R.id.lunch_card)
        lunchView = findViewById(R.id.lunch_view)
        btnScan = findViewById(R.id.btnScan)
        resultText = findViewById(R.id.resultText)


        nameTextView.text = intent.getStringExtra("Name")

        dinnerCardView = findViewById(R.id.dinner_card)
        dinnerView = findViewById(R.id.dinner_view)

        snackCardView = findViewById(R.id.snack_card)
        snackView = findViewById(R.id.snack_view)

        breakfastCardView.setOnClickListener{
            if (breakfastView.visibility == View.VISIBLE) {
//                TransitionManager.beginDelayedTransition(breakfastCardView, AutoTransition());
                breakfastView.visibility = (View.GONE);
//                arrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
            }else {
//                TransitionManager.beginDelayedTransition(breakfastCardView, AutoTransition());
                breakfastView.visibility = (View.VISIBLE);
//                arrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
            }
        }

        lunchCardView.setOnClickListener{
            if (lunchView.visibility == View.VISIBLE) {
                lunchView.visibility = (View.GONE);
            }else {
//                TransitionManager.beginDelayedTransition(lunchCardView, AutoTransition());
                lunchView.visibility = (View.VISIBLE);
            }
        }

        dinnerCardView.setOnClickListener{
            if (dinnerView.visibility == View.VISIBLE) {
                dinnerView.visibility = (View.GONE);
            }else {
//                TransitionManager.beginDelayedTransition(dinnerCardView, AutoTransition());
                dinnerView.visibility = (View.VISIBLE);
            }
        }

        snackCardView.setOnClickListener{
            if (snackView.visibility == View.VISIBLE) {
                snackView.visibility = (View.GONE);
            }else {
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
                c.set(year,monthOfYear,dayOfMonth)
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


        btnScan.setOnClickListener {
            /*val intent = Intent(applicationContext, ScanActivity::class.java)
            startActivity(intent)*/
            openCameraActivityForResult()
        }

        val result = data?.getStringExtra(RESULT)

        if(result != null)
            resultText.text = result.toString()

    }


        breakfastRecyclerView.layoutManager = LinearLayoutManager(this)
        lunchRecyclerView.layoutManager = LinearLayoutManager(this)
        dinnerRecyclerView.layoutManager = LinearLayoutManager(this)
        snackRecyclerView.layoutManager = LinearLayoutManager(this)

        dataSource = DailyFoodLogDataSource.getDataSource()
        dataSource.getDBFoods(date)
        val liveList = dataSource.getFoodList()

        liveList.observe(this) { it ->
            it?.let { list ->
                breakfastAdapter = DailyFoodItemAdapter(this, list.filter { it.meal.equals("breakfast") })
                breakfastRecyclerView.adapter = breakfastAdapter

                lunchAdapter = DailyFoodItemAdapter(this, list.filter { it.meal.equals("lunch") })
                lunchRecyclerView.adapter = lunchAdapter

                dinnerAdapter = DailyFoodItemAdapter(this, list.filter { it.meal.equals("dinner") })
                dinnerRecyclerView.adapter = dinnerAdapter

                snackAdapter = DailyFoodItemAdapter(this, list.filter { it.meal.equals("snacks")})
                snackRecyclerView.adapter = snackAdapter
            }
        }

    }

    private fun openCameraActivityForResult() {
        val intent = Intent(this, ScanActivity::class.java)
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == CAMERARESULT) {
            // There are no request codes
            data = result.data
            val result = data?.getStringExtra(RESULT)

            if(result != null)
                resultText.text = result.toString()
        }
    }

}