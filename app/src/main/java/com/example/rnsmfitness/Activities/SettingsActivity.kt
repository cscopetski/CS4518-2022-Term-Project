package com.example.rnsmfitness.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rnsmfitness.RetroFitClient
import com.example.rnsmfitness.databinding.ActivitySettingsBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TAG = "SETTINGS"

class SettingsActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.logoutButton.setOnClickListener{
            logout(this)
        }
        binding.backButton.setOnClickListener {
            finish()
        }
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
}