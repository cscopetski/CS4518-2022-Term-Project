package com.example.rnsmfitness.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Entities.DataSource
import com.example.rnsmfitness.Entities.FoodItem
import com.example.rnsmfitness.Entities.FoodItemBody
import com.example.rnsmfitness.FoodItemAdapter
import com.example.rnsmfitness.R
import com.example.rnsmfitness.RetroFitClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG1 = "MyFoodList"

class MyFoodList : AppCompatActivity() {

    private var foods: MutableLiveData<List<FoodItem>> = MutableLiveData(listOf<FoodItem>())
    lateinit var createFoodButton: FloatingActionButton
    lateinit var homeButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private val createFoodActivityRequestCode = 1

    private var adapter: FoodItemAdapter = FoodItemAdapter(this,

        foods.value!!
    )

    private lateinit var dataSource: DataSource



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_food_list)

        createFoodButton = findViewById(R.id.add_food_fab)
        homeButton = findViewById(R.id.home_buton)
        recyclerView = findViewById(R.id.foodRecycler)

        getDBFoods()


        homeButton.setOnClickListener {
            //set current view to myFoodPage
            switchToHomePage()
        }

        createFoodButton.setOnClickListener {
            //set current view to myFoodPage
            switchToCreateFood()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        dataSource = DataSource.getDataSource()



        val liveList = dataSource.getFoodList()

        liveList.observe(this) {
            it?.let {
                adapter = FoodItemAdapter(this, it)
                recyclerView.adapter = adapter
            }
        }

        setRecyclerViewItemTouchListener()


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





}