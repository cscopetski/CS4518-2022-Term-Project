package com.example.rnsmfitness.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.rnsmfitness.R

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    fun radio_button_click(view: View) {}
    fun WeightGoal1Click(view: View) {}
    fun WeightGoal2Click(view: View) {}
    fun WeightGoal3Click(view: View) {}
    fun onActiveLevelButtonClick(view: View) {}
    fun onSexClick(view: View) {}
    fun WeightGoalClick(view: View) {}
}