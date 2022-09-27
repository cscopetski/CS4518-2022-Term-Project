package com.example.rnsmfitness.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.rnsmfitness.Entities.LoginCredentials
import com.example.rnsmfitness.Entities.User
import com.example.rnsmfitness.R
import com.example.rnsmfitness.RetroFitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    private var users: MutableLiveData<User> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.login_button)
        emailText = findViewById(R.id.email_editText)
        passwordText = findViewById(R.id.password_editText)

        loginButton.setOnClickListener {


            if(emailText.text.isEmpty() || passwordText.text.isEmpty()){
                loginButton.error = "Must fill out both fields"
                Toast.makeText(this, "Must fill out both fields", Toast.LENGTH_LONG).show()
            }else{
                loginButton.error = null
                login(emailText.text.toString(), passwordText.text.toString(), this)
            }


        }


        if(RetroFitClient.checkCookie()){
            Toast.makeText(this, "COOKIE FOUND, LOGGING IN", Toast.LENGTH_LONG).show()
            getUser()
            this.users.observe(this) {
                switchActivity(users.value)
            }
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

                    switchActivity(user)
                } else {
                    Log.d(TAG, "Incorrect Login")
                    loginButton.error = "Incorrect Email or Password"
                    Toast.makeText(activity, "Failed to Login:\nIncorrect Email or Password", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<User?>?, t: Throwable?) {
                Log.d(TAG, "Login: Error")
                Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun getUser(){
        val call: Call<User> = RetroFitClient.userService.getUser()
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user: User = response.body()!!
                    Log.d(TAG, "Get User:\n" + user.firstname)
                    users.postValue(user)

                } else {

                    Log.d(TAG, "Failed to get user")
                    users.postValue(null)

                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d(TAG, "Get User: Error")
                users.postValue(null)
            }

        })
    }

    private fun switchActivity(user: User?){
        if(user!=null){
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("Name",user.firstname + " " + user.lastname)
            startActivity(intent)
        }else{
            Log.d(TAG, "Switch Activity: User Error")
        }

    }
}