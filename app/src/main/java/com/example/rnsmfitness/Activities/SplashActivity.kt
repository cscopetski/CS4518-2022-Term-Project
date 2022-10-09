package com.example.rnsmfitness.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    /** Duration of wait  */
    private val SPLASH_DISPLAY_LENGTH = 1000

    /** Called when the activity is first created.  */
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(com.example.rnsmfitness.R.layout.splash_screen)

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/Handler().postDelayed({ /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this@SplashActivity, HomeActivity::class.java)
            this@SplashActivity.startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}