package com.example.rnsmfitness.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


private const val TAG = "USDAFoodList"

class USDAFoodList : AppCompatActivity() {

    private var foods: MutableLiveData<List<USDAFoodItem>> = MutableLiveData(listOf<USDAFoodItem>())
//    lateinit var homeButton: FloatingActionButton
    lateinit var searchUSDA: Button
    private lateinit var recyclerView: RecyclerView
    lateinit var searchView: SearchView
    private lateinit var navBar:BottomNavigationView

    private var adapter: USDAFoodItemAdapter = USDAFoodItemAdapter(this,
        foods.value!!
    )

    private lateinit var usdaDataSource: USDADataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usda_food_list)

//        homeButton = findViewById(R.id.home_buton)
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

        //getDBFoods()

//        homeButton.setOnClickListener {
//            //set current view to myFoodPage
//            switchToHomePage()
//        }

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


//    private fun setRecyclerViewItemTouchListener() {
//
//
//        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//
//            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
//            }
//        }
//
//        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(recyclerView)
//    }

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

}