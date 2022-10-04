package com.example.rnsmfitness.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.rnsmfitness.Activities.ScanActivity.Companion.CAMERARESULT
import com.example.rnsmfitness.Entities.LoginCredentials
import com.example.rnsmfitness.Entities.USDAFoodItem
import com.example.rnsmfitness.Entities.User
import com.example.rnsmfitness.R
import com.example.rnsmfitness.RetroFitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class HomeActivity : AppCompatActivity() {

    lateinit var myFoodButton: Button
    lateinit var logoutButton: Button
    lateinit var nameTextView: TextView

    lateinit var testUPCButton: Button
    lateinit var testSearchButton: Button

    lateinit var btnScan: Button
    lateinit var resultText: TextView

    var data: Intent? = null

    companion object{
        const val RESULT = "RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        myFoodButton = findViewById(R.id.myfood_page_button)
        logoutButton = findViewById(R.id.logout_button)
        nameTextView = findViewById(R.id.name_text)

        testUPCButton = findViewById(R.id.test_upc)
        testSearchButton = findViewById(R.id.test_search)

        btnScan = findViewById(R.id.btnScan)
        resultText = findViewById(R.id.resultText)

        nameTextView.text = intent.getStringExtra("Name")

        myFoodButton.setOnClickListener {
            //set current view to myFoodPage
            switchToMyFoodPage()
        }

        logoutButton.setOnClickListener{
            logout(this)
        }

        testUPCButton.setOnClickListener{

            testUPC("863812000022")
        }

        testSearchButton.setOnClickListener{
            testSearch("Burger")
        }


        btnScan.setOnClickListener {
            /*val intent = Intent(applicationContext, ScanActivity::class.java)
            startActivity(intent)*/
            openCameraActivityForResult()
        }

        val result = data?.getStringExtra(RESULT)

        if(result != null)
            resultText.text = result.toString()

    }

    private fun testUPC(upcId : String){

        val call: Call<USDAFoodItem> =
            RetroFitClient.usdaFoodService.getFoodByUPCId(upcId);

        call.enqueue(object : Callback<USDAFoodItem?> {
            override fun onResponse(call: Call<USDAFoodItem?>, response: Response<USDAFoodItem?>) {
                if(response.isSuccessful){
                    Log.d(TAG,response.body().toString())
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

    private fun testSearch(query : String){

        val call: Call<List<USDAFoodItem>> =
            RetroFitClient.usdaFoodService.searchFood(query);

        call.enqueue(object : Callback<List<USDAFoodItem>> {

            override fun onResponse(
                call: Call<List<USDAFoodItem>>,
                response: Response<List<USDAFoodItem>>
            ) {
                if(response.isSuccessful){
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

    private fun logout(activity: Context){

        val call: Call<ResponseBody> =
            RetroFitClient.authorizationService.logout()

        call.enqueue(object : Callback<ResponseBody?> {

            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {

                if (response.isSuccessful) {

                    Toast.makeText(activity, "Logout User", Toast.LENGTH_LONG).show()
                    RetroFitClient.deleteCookie()
                    switchActivity()
                } else {
                    Log.d(TAG, "Logout Failed")

                    Toast.makeText(activity, "Failed to Logout", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {
                Log.d(TAG, "Logout: Error")
                Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun switchActivity(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
    }

    private fun switchToMyFoodPage(){
        val intent = Intent(this, MyFoodList::class.java)
        startActivity(intent)
    }

    fun openCameraActivityForResult() {
        val intent = Intent(this, ScanActivity::class.java)
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == CAMERARESULT) {
            // There are no request codes
            data = result.data

        }
    }

}