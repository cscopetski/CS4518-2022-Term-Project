package com.example.rnsmfitness.Activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rnsmfitness.Entities.LoginCredentials
import com.example.rnsmfitness.Entities.User
import com.example.rnsmfitness.R
import com.example.rnsmfitness.RetroFitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "MainActivity";

class MainActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton = findViewById(R.id.login_button)
        emailText = findViewById(R.id.email_editText)
        passwordText = findViewById(R.id.password_editText)

        loginButton.setOnClickListener {
            login("cscopetski@gmail.com", "123", this)
        }

        if(RetroFitClient.checkCookie()){
            Toast.makeText(this, "COOKIE FOUND, LOGGING IN", Toast.LENGTH_LONG).show()
        }

    }

    private fun login(email: String, password: String, activity:Context) {

        val call: Call<User> =
            RetroFitClient.authorizationService.login(LoginCredentials(email, password))

        call.enqueue(object : Callback<User?> {

            override fun onResponse(call: Call<User?>?, response: Response<User?>) {

                if (response.isSuccessful) {
                    val user: User = response.body()!!

                    Log.d(TAG, "Login:\n" + user.firstname)

                    Toast.makeText(activity, "Login " + user.firstname, Toast.LENGTH_LONG).show()
                } else {
                    Log.d(TAG, "Incorrect Login")

                    Toast.makeText(activity, "Failed to Login:\nIncorrect Email or Password", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<User?>?, t: Throwable?) {
                Log.d(TAG, "Login: Error")
                Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }
}