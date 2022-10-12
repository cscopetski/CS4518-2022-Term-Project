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

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.login_button)
        signUpButton = findViewById(R.id.signup)
        emailText = findViewById(R.id.email_editText)
        passwordText = findViewById(R.id.password_editText)

        loginButton.setOnClickListener {


            if (emailText.text.isEmpty() || passwordText.text.isEmpty()) {
                loginButton.error = "Must fill out both fields"
                Toast.makeText(this, "Must fill out both fields", Toast.LENGTH_LONG).show()
            } else {
                loginButton.error = null
                login(emailText.text.toString(), passwordText.text.toString(), this)
            }


        }

        signUpButton.setOnClickListener {
            signUp()
        }


    }

    private fun login(email: String, password: String, activity: Context) {

        val call: Call<User> =
            RetroFitClient.authorizationService.login(LoginCredentials(email, password))

        call.enqueue(object : Callback<User?> {

            override fun onResponse(call: Call<User?>?, response: Response<User?>) {

                if (response.isSuccessful) {
                    switchActivity()
                } else {
                    Log.d(TAG, "Incorrect Login")
                    loginButton.error = "Incorrect Email or Password"
                    Toast.makeText(
                        activity,
                        "Failed to Login:\nIncorrect Email or Password",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<User?>?, t: Throwable?) {
                Log.d(TAG, "Login: Error")
                Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun switchActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun signUp() {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
        finish()
    }

}