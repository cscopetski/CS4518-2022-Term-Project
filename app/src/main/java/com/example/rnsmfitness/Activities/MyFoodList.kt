package com.example.rnsmfitness.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rnsmfitness.Entities.DataSource
import com.example.rnsmfitness.Entities.FoodItem
import com.example.rnsmfitness.FoodItemAdapter
import com.example.rnsmfitness.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val TAG1 = "MyFoodList"


fun getFoods(): List<FoodItem> {

    var id: Int
    var user_id: Int
    var title: String
    var protein: Int
    var carbs: Int
    var fat: Int
    var calories: Int
    var serving_size: Double
    val foods = ArrayList<FoodItem>()

    for (i in 1..20){
        id = i
        user_id = 100
        title = "Food $i"
        protein = i
        carbs = i
        fat = i
        calories = 4 * protein + 4 * carbs + 9 * fat
        serving_size = i.toDouble()


        foods.add(
            FoodItem(id, user_id, title, true,protein, fat, carbs, calories, serving_size)
        )
    }

    return foods
}

class MyFoodList : AppCompatActivity() {

    lateinit var createFoodButton: FloatingActionButton
    lateinit var homeButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private val createFoodActivityRequestCode = 1

    private var adapter: FoodItemAdapter = FoodItemAdapter(this,
        getFoods()
    )

    private lateinit var dataSource: DataSource



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_food_list)

        createFoodButton = findViewById(R.id.add_food_fab)
        homeButton = findViewById(R.id.home_buton)
        recyclerView = findViewById(R.id.foodRecycler)


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
                    dataSource.deleteFood(curFood)
                }

            }
        }

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(recyclerView)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        Log.d(TAG1, "adding new food")
        if (requestCode == createFoodActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val foodName = data.getStringExtra("name")
                val foodProtein = data.getIntExtra("protein", 0)
                val foodCarbs = data.getIntExtra("carbs",0)
                val foodFat = data.getIntExtra("fat",0)
                val servingSize = data.getDoubleExtra("serving_size", 0.0)


                dataSource.insertFood(foodName,foodProtein,foodCarbs,foodFat, servingSize)
            }
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


}