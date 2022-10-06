package com.example.rnsmfitness.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import com.example.rnsmfitness.R
import com.example.rnsmfitness.databinding.ActivitySignUpBinding
import java.util.*

class SignUp : AppCompatActivity() {

    //Binding
    lateinit var binding: ActivitySignUpBinding

    //Sign Up Credentials

    //ActivityLevel

    var activityLevel: Int? = null  //activityLevel = 0,1,2,3


    //Weight Goal

    var weightGoal: Int? = null  //weightGoal = 0,1,2


    //Measurements

    var height: Double? = null
    var heightCm: Double? = null
    var heightIn: Double? = null
    var heightFt: Double? = null

    var weight: Double? = null
    var weightkg: Double? = null
    var weightlb: Double? = null

    var goalWeight: Double? = null
    var goalWeightlb: Double? = null
    var goalWeightkg: Double? = null


    // Sex/DOB

    var sex: String? = null

    var dob: Date? = null


    //Sign Up

    var firstName : String? = null

    var lastName : String? = null

    var email : String? = null

    var password : String? = null

    var confirmPassword : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        binding.HeightInch.doOnTextChanged { text, start, before, count ->
            heightIn = text.toString().toDouble()
        }

        binding.HeightFeet.doOnTextChanged { text, start, before, count ->
            heightFt = text.toString().toDouble()
        }

        binding.HeightCm.doOnTextChanged { text, start, before, count ->
            heightCm = text.toString().toDouble()
        }

        binding.currentWeightlb.doOnTextChanged { text, start, before, count ->
            weightlb = text.toString().toDouble()
        }

        binding.currentWeightKg.doOnTextChanged { text, start, before, count ->
            weightkg = text.toString().toDouble()
        }

        binding.goalWeightlb.doOnTextChanged { text, start, before, count ->
            goalWeightlb = text.toString().toDouble()
        }

        binding.goalWeightkg.doOnTextChanged { text, start, before, count ->
            goalWeightkg = text.toString().toDouble()
        }
        val today = Calendar.getInstance()
        binding.DOB.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)){
            view, year, month, day ->
            dob = Date(year,month,day)
        }

    }

    fun onActiveLevelButtonClick(view: View) {
        if(view.id == R.id.sedentary){
            activityLevel = 0
        } else if(view.id == R.id.lightlyActive){
            activityLevel = 1
        } else if(view.id == R.id.active){
            activityLevel = 2
        } else if(view.id == R.id.veryActive){
            activityLevel = 3
        } else{
            activityLevel = null
        }
    }

    fun WeightGoalClick(view: View) {
        if(view.id == R.id.loseWeight){
            weightGoal = 0
        } else if(view.id == R.id.maintainWeight){
            weightGoal = 1
        } else if(view.id == R.id.gainWeight){
            weightGoal = 2
        } else{
            weightGoal = null
        }
    }

    fun onSexClick(view: View) {
        if(view.id == R.id.Male){
            sex = "Male"
        } else if(view.id == R.id.Female){
            sex = "Female"
        } else{
            sex = null
        }
    }
}
