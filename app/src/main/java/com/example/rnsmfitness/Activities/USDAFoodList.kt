package com.example.rnsmfitness.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Activities.ScanActivity.Companion.CAMERARESULT
import com.example.rnsmfitness.Entities.DailyFoodItem
import com.example.rnsmfitness.Entities.FoodItem
import com.example.rnsmfitness.Entities.USDADataSource
import com.example.rnsmfitness.Entities.USDAFoodItem
import com.example.rnsmfitness.FoodItemAdapter
import com.example.rnsmfitness.R
import com.example.rnsmfitness.RetroFitClient
import com.example.rnsmfitness.USDAFoodItemAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


private const val TAG = "USDAFoodList"

class USDAFoodList : AppCompatActivity() {

    private var foods: MutableLiveData<List<USDAFoodItem>> = MutableLiveData(listOf<USDAFoodItem>())
    private lateinit var scanButton: FloatingActionButton
    private lateinit var searchUSDA: Button
    private lateinit var scanData: Intent
    private lateinit var recyclerView: RecyclerView
    lateinit var searchView: SearchView
    private lateinit var navBar:BottomNavigationView
    private lateinit var date: Date
    private var adapter: USDAFoodItemAdapter = USDAFoodItemAdapter(this,
        foods.value!!
    )

    private lateinit var usdaDataSource: USDADataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usda_food_list)
        date = Date(intent.getLongExtra("Date",System.currentTimeMillis()))
        scanButton = findViewById(R.id.scan_food_button)
        recyclerView = findViewById(R.id.usda_foodRecycler)
        searchView = findViewById(R.id.my_food_search)
        searchUSDA = findViewById(R.id.usda_search_button)

        navBar = findViewById(R.id.bottom_nav)
        navBar.selectedItemId = (R.id.foods)

        navBar.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> startActivity(Intent(this, HomeActivity::class.java))
                R.id.settings -> startActivity(Intent(this, SettingsActivity::class.java))
            }
            overridePendingTransition(0, 0);
            true
        })

        scanButton.setOnClickListener {
            openCameraActivityForResult()
        }

        searchUSDA.setOnClickListener {
            getUSDAFoods(searchView.query.toString())
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        usdaDataSource = USDADataSource.getDataSource()

        val liveList = usdaDataSource.getFoodList()

        liveList.observe(this) {
            it?.let {
                adapter = USDAFoodItemAdapter(this, it)
                recyclerView.adapter = adapter
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        Log.d(TAG, "adding new food")
        if (resultCode == Activity.RESULT_OK) {
            getUSDAFoods(searchView.query.toString())
        }
    }

    private fun switchToHomePage(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun getUSDAFoods(query : String){

        val call: Call<List<USDAFoodItem>> =
            RetroFitClient.usdaFoodService.searchFood(query);

        call.enqueue(object : Callback<List<USDAFoodItem>> {

            override fun onResponse(
                call: Call<List<USDAFoodItem>>,
                response: Response<List<USDAFoodItem>>
            ) {
                if(response.isSuccessful){
                    val foodList: List<USDAFoodItem> = response.body()!!
                    usdaDataSource.setFoodList(foodList)
                    Log.d(TAG,response.body().toString())
                }else{
                    Log.d(TAG,response.code().toString())
                    Log.d(TAG,response.message())
                }
            }

            override fun onFailure(call: Call<List<USDAFoodItem>>, t: Throwable) {
                Log.d(TAG,t.toString())
            }
        })
    }

    private fun openCameraActivityForResult() {
        val intent = Intent(this, ScanActivity::class.java)
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == CAMERARESULT) {
            // There are no request codes
            scanData = result.data!!
            val result = scanData.getStringExtra(HomeActivity.RESULT)

            if(result != null)
                getFoodByUPC(result.toString())
        }
    }

    private fun getFoodByUPC(upcId : String){

        val call: Call<USDAFoodItem> =
            RetroFitClient.usdaFoodService.getFoodByUPCId(upcId);

        call.enqueue(object : Callback<USDAFoodItem?> {
            override fun onResponse(call: Call<USDAFoodItem?>, response: Response<USDAFoodItem?>) {
                if(response.isSuccessful){

                    Log.d(TAG,response.body().toString())
                    val list = ArrayList<USDAFoodItem>()
                    list.add(response.body()!!)
                    usdaDataSource.setFoodList(list)
                }else{
                    Log.d(TAG,response.code().toString())
                    Log.d(TAG,response.message())
                }
            }

            override fun onFailure(call: Call<USDAFoodItem?>, t: Throwable) {
                Log.d(TAG,t.toString())
            }
        })
    }

}