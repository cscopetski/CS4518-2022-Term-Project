package com.example.rnsmfitness.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rnsmfitness.RetroFitClient

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH = 1000

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(com.example.rnsmfitness.R.layout.splash_screen)

            Handler().postDelayed({
            var mainIntent: Intent
            if(RetroFitClient.checkCookie()){
                mainIntent = Intent(this@SplashActivity, HomeActivity::class.java)

            }else {
                mainIntent = Intent(this@SplashActivity, LoginActivity::class.java)
            }

            this@SplashActivity.startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}