package com.example.rnsmfitness.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Entities.DailyFoodItem
import com.example.rnsmfitness.Entities.DataSource
import com.example.rnsmfitness.Entities.FoodItem
import com.example.rnsmfitness.FoodItemAdapter
import com.example.rnsmfitness.R
import com.example.rnsmfitness.RetroFitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.util.*


private const val TAG1 = "MyFoodList"

class MyFoodList : AppCompatActivity() {

    private var foods: MutableLiveData<List<FoodItem>> = MutableLiveData(listOf<FoodItem>())
    lateinit var createFoodButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    lateinit var searchView: SearchView
    private val createFoodActivityRequestCode = 1
    private lateinit var liveList: LiveData<List<FoodItem>>
    private lateinit var navBar: BottomNavigationView

    private lateinit var date: Date

    private lateinit var myFoodButton: Button
    private lateinit var usdaButton: Button


    private var adapter: FoodItemAdapter = FoodItemAdapter(this,
        foods.value!!, Date(System.currentTimeMillis())
    )

    private lateinit var dataSource: DataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_food_list)
        date = Date(intent.getLongExtra("Date",System.currentTimeMillis()))
        createFoodButton = findViewById(R.id.add_food_fab)
        recyclerView = findViewById(R.id.foodRecycler)
        searchView = findViewById(R.id.my_food_search)

        navBar = findViewById(R.id.bottom_nav)
        navBar.selectedItemId = (R.id.foods)

        myFoodButton = findViewById(R.id.my_food_button)
        usdaButton = findViewById(R.id.usda_button)

        navBar.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> startActivity(Intent(this, HomeActivity::class.java))
                R.id.settings -> startActivity(Intent(this, SettingsActivity::class.java))
            }
            overridePendingTransition(0, 0);
            true
        })

        getDBFoods()

        createFoodButton.setOnClickListener {
            //set current view to myFoodPage
            switchToCreateFood()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        dataSource = DataSource.getDataSource()

        myFoodButton.isEnabled = false

        usdaButton.setOnClickListener{
            switchToUSDAPage()
            overridePendingTransition(0, 0);
        }

        val liveList = dataSource.getFoodList()

        liveList.observe(this) {
            it?.let {
                adapter = FoodItemAdapter(this, it, date)
                recyclerView.adapter = adapter
            }
        }




        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter(query)
                //adapter = FoodItemAdapter(this@MyFoodList, liveList.value!!.filter { it.name.contains(query)})
                //recyclerView.adapter = adapter
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter(newText)
                //adapter = FoodItemAdapter(this@MyFoodList, liveList.value!!.filter { it.name.contains(newText)})
                //recyclerView.adapter = adapter
                return true
            }
        })


        setRecyclerViewItemTouchListener()




    }

    override fun onResume() {
        super.onResume()
        getDBFoods()
        liveList = dataSource.getFoodList()
        navBar.selectedItemId = (R.id.foods)
    }


    private fun setRecyclerViewItemTouchListener() {


        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val curFood: FoodItem? =  dataSource.getFoodList().value?.get(viewHolder.bindingAdapterPosition)
                if (curFood != null){
                    deleteFood(curFood)
                    Log.v(TAG1, "Food has been swiped")

                }

            }
        }

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(recyclerView)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        Log.d(TAG1, "adding new food")
        if (resultCode == Activity.RESULT_OK) {
            Log.v(TAG1, "Right before calling getDBFoods")
            getDBFoods()
        }
    }


    private fun switchToHomePage(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun switchToUSDAPage(){
        val intent = Intent(this, USDAFoodList::class.java)
        startActivity(intent)
    }

    private fun switchToCreateFood(){
        val intent = Intent(this, CreateFoodActivity::class.java)
        startActivityForResult(intent,createFoodActivityRequestCode)
    }

    private fun getDBFoods(){
        val call: Call<List<FoodItem>> =
            RetroFitClient.foodService.getAllUserFoods()

        call.enqueue(object : Callback<List<FoodItem>> {

            override fun onResponse(call: Call<List<FoodItem>>, response: Response<List<FoodItem>>) {

                if (response.isSuccessful) {
                    Log.v(TAG1, response.body().toString())
                    val foodList: List<FoodItem> = response.body()!!
                    dataSource.setFoodList(foodList)
                } else {
                    dataSource.setFoodList(null)
                    Log.d(TAG1, response.code().toString())

                }
            }

            override fun onFailure(call: Call<List<FoodItem>>, t: Throwable) {

                dataSource.setFoodList(null)
                Log.d(TAG1, t.message!!)

            }
        })
    }

    private fun deleteFood(food:FoodItem){
        //set food visibility to 0 aka delete it
        food.is_visible=0

        val call: Call<ResponseBody> =
            RetroFitClient.foodService.deleteFood(food)

        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.v(TAG1, "in Delete on Response")
                if(response.isSuccessful){
                    Log.v(TAG1, "in if")

                }else{
                    Log.v(TAG1, "in else")
                    Log.v(TAG1, response.code().toString())

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.v(TAG1, "In delete on failure")
            }
        })
    }



    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val currentList = dataSource.getFoodList().value
        val filteredlist: ArrayList<FoodItem> = ArrayList<FoodItem>()

        // running a for loop to compare elements.
        for (i in 0..(dataSource.getFoodList().value?.size!!)) {
            // checking if the entered string matched with any item of our recycler view.
            if (currentList != null) {
                if (currentList.get(i).name.toLowerCase().contains(text.lowercase(Locale.getDefault()))) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(currentList.get(i))
                }
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Foods Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.

            //adapter.filterList(filteredlist)
            dataSource.setFoodList(filteredlist)
        }
    }

}





