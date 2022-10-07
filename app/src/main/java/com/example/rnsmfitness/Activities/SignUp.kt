package com.example.rnsmfitness.Activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.rnsmfitness.R
import com.example.rnsmfitness.databinding.ActivitySignUpBinding
import java.util.*


const val SIGNUP = "SIGNUP"

class SignUp : AppCompatActivity() {

    //Binding
    lateinit var binding: ActivitySignUpBinding

    //Sign Up Credentials

    //ActivityLevel

    var activityLevel: Int? = null  //activityLevel = 0,1,2,3


    //Weight Goal

    var weightGoal: Int? = null  //weightGoal = 0,1,2


    //Measurements
    //0 for Imperial; 1 for Metric
    var measurement: Int = 0

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

    var datePickerDialog: DatePickerDialog? = null
    lateinit var date : EditText

    //Sign Up

    var firstName : String? = null

    var lastName : String? = null

    var email : String? = null

    var password : String? = null

    var confirmPassword : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Binding

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        date = binding.date

        //Set Listeners

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

        binding.FirstName.doOnTextChanged{text, start, before, count ->
            firstName = text.toString()
        }

        binding.LastName.doOnTextChanged{text, start, before, count ->
            lastName = text.toString()
        }

        binding.email.doOnTextChanged{text, start, before, count ->
            email = text.toString()
        }

        binding.password.doOnTextChanged{text, start, before, count ->
            password = text.toString()
        }

        binding.confirmPassword.doOnTextChanged{text, start, before, count ->
            confirmPassword = text.toString()
        }


        //Set on clicker

        date.setOnClickListener(View.OnClickListener { // calender class's instance and get current date , month and year from calender
            val c = Calendar.getInstance()
            val mYear = c[Calendar.YEAR] // current year
            val mMonth = c[Calendar.MONTH] // current month
            val mDay = c[Calendar.DAY_OF_MONTH] // current day
            // date picker dialog
            datePickerDialog = DatePickerDialog(this@SignUp,
                { view, year, monthOfYear, dayOfMonth -> // set day of month , month and year value in the edit text
                    date.setText(
                        dayOfMonth.toString() + "/"
                                + (monthOfYear + 1) + "/" + year
                    )
                }, mYear, mMonth, mDay
            )
            datePickerDialog!!.show()
        })

        //Next Buttons

        binding.ActivityLevelNextButton.setOnClickListener {
            Log.d(SIGNUP, "ActivityLevelNextButton Clicked")
            binding.ActivityLevelHiddenView.visibility = View.GONE
            binding.WeightGoalHiddenView.visibility = View.VISIBLE
        }

        binding.WeightGoalNextButton.setOnClickListener {
            binding.WeightGoalHiddenView.visibility = View.GONE
            binding.MeasurementImperialHiddenView.visibility = View.VISIBLE
            binding.ImpMetricButtons.visibility = View.VISIBLE
        }

        binding.MetricButton.setOnClickListener {
            if(measurement == 0){
                binding.MeasurementImperialHiddenView.visibility = View.GONE
                binding.MeasurementMetricHiddenView.visibility = View.VISIBLE
                measurement = 1
            }
        }

        binding.ImperialButton.setOnClickListener {
            if(measurement == 1){
                binding.MeasurementMetricHiddenView.visibility = View.GONE
                binding.MeasurementImperialHiddenView.visibility = View.VISIBLE
                measurement = 0
            }
        }

        binding.MeasurementNextButton.setOnClickListener {
            binding.MeasurementImperialHiddenView.visibility = View.GONE
            binding.MeasurementMetricHiddenView.visibility = View.GONE
            binding.ImpMetricButtons.visibility = View.GONE
            binding.SexDOBHiddenView.visibility = View.VISIBLE
        }

        binding.SexDOBNextButton.setOnClickListener {
            binding.SexDOBHiddenView.visibility = View.GONE
            binding.SignUpHiddenView.visibility = View.VISIBLE
        }

        binding.signUpButton.setOnClickListener {
            signUp()
        }



    }

    private fun signUp() {
        TODO("Not yet implemented")
    }

    //On Click Functions

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
