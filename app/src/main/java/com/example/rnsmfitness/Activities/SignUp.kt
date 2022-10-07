package com.example.rnsmfitness.Activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.rnsmfitness.ModelViews.SignUpModelView
import com.example.rnsmfitness.R
import com.example.rnsmfitness.databinding.ActivitySignUpBinding
import java.util.*
import kotlin.math.sign


const val SIGNUP = "SIGNUP"

class SignUp : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    var datePickerDialog: DatePickerDialog? = null

    private val signUpFields: SignUpModelView by lazy{
        ViewModelProvider(this)[SignUpModelView::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Set Listeners
        binding.HeightInch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(binding.HeightInch.text.isNullOrEmpty() || s.toString() == ""){
                    signUpFields.heightIn = null
                }else
                    signUpFields.heightIn = s.toString().toDouble()
            }
        })

        binding.HeightFeet.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(binding.HeightFeet.text.isNullOrEmpty()||s.toString() == ""){
                    signUpFields.heightFt = null
                }else
                    signUpFields.heightFt = s.toString().toDouble()
            }
        })

        binding.HeightCm.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(binding.HeightCm.text.isNullOrEmpty() || s.toString() == ""){
                    signUpFields.heightCm = null
                }else
                    signUpFields.heightCm = s.toString().toDouble()
            }
        })

        binding.currentWeightlb.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(binding.currentWeightlb.text.isNullOrEmpty() || s.toString() == ""){
                    signUpFields.weightlb = null
                }else
                    signUpFields.weightlb = s.toString().toDouble()
            }
        })

        binding.currentWeightKg.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(binding.currentWeightKg.text.isNullOrEmpty() || s.toString() == ""){
                    signUpFields.weightkg = null
                }else
                    signUpFields.weightkg = s.toString().toDouble()
            }
        })

        binding.goalWeightlb.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(binding.goalWeightlb.text.isNullOrEmpty() ||s.toString() == ""){
                    signUpFields.goalWeightlb = null
                }else
                    signUpFields.goalWeightlb = s.toString().toDouble()
            }
        })

        binding.goalWeightkg.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(binding.goalWeightkg.text.isNullOrEmpty() || s.toString() == ""){
                    signUpFields.goalWeightkg = null
                }else
                    signUpFields.goalWeightkg = s.toString().toDouble()
            }
        })

        binding.FirstName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(binding.FirstName.text.isNullOrEmpty() || s.toString() == ""){
                    signUpFields.firstName = null
                }else
                    signUpFields.firstName = s.toString()
            }
        })

        binding.LastName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(binding.LastName.text.isNullOrEmpty() || s.toString() == ""){
                    signUpFields.lastName = null
                }else
                    signUpFields.lastName = s.toString()
            }
        })

        binding.email.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(binding.email.text.isNullOrEmpty() || s.toString() == ""){
                    signUpFields.email = null
                }else
                    signUpFields.email = s.toString()
            }
        })

        binding.password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(binding.password.text.isNullOrEmpty() || s.toString() == ""){
                    signUpFields.password = null
                }else
                    signUpFields.password = s.toString()
            }
        })

        binding.confirmPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(binding.confirmPassword.text.isNullOrEmpty() || s.toString() == ""){
                    signUpFields.confirmPassword = null
                }else
                    signUpFields.confirmPassword = s.toString()
            }
        })

        //Set on clicker
        binding.date.setOnClickListener(View.OnClickListener { // calender class's instance and get current date , month and year from calender
            val c = Calendar.getInstance()
            val mYear = c[Calendar.YEAR] // current year
            val mMonth = c[Calendar.MONTH] // current month
            val mDay = c[Calendar.DAY_OF_MONTH] // current day
            // date picker dialog
            datePickerDialog = DatePickerDialog(this@SignUp,
                { view, year, monthOfYear, dayOfMonth -> // set day of month , month and year value in the edit text
                    binding.date.text = dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
                    c.set(Calendar.YEAR, year)
                    c.set(Calendar.MONTH, monthOfYear)
                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    signUpFields.dob = c.time
                    Log.d(SIGNUP, signUpFields.dob.toString())
                }, mYear, mMonth, mDay
            )
            datePickerDialog!!.show()
        })


        //Click on section/Header
        binding.ActivityLevelHeader.setOnClickListener {
            activityLevelPage()
        }
        binding.WeightGoalHeader.setOnClickListener {
            weightGoalPage()
        }
        binding.MeasurementHeader.setOnClickListener {
            measurementPage()
        }
        binding.SexDOBHeader.setOnClickListener {
            sexDOBPage()
        }
        binding.SignUpHeader.setOnClickListener {
            signUpPage()
        }


        //Next Buttons
        binding.ActivityLevelNextButton.setOnClickListener {
            weightGoalPage()
        }

        binding.WeightGoalNextButton.setOnClickListener {
            measurementPage()
        }

        binding.MeasurementNextButton.setOnClickListener {
            sexDOBPage()
        }

        binding.SexDOBNextButton.setOnClickListener {
            signUpPage()
        }


        //Back Buttons
        binding.WeightGoalBackButton.setOnClickListener {
            activityLevelPage()
        }

        binding.MeasurementBackButton.setOnClickListener {
            weightGoalPage()
        }

        binding.SexDOBBackButton.setOnClickListener {
            measurementPage()
        }

        binding.SignUpBackButton.setOnClickListener {
            sexDOBPage()
        }


        //Sign Up Button
        binding.signUpButton.setOnClickListener {
            signUp()
        }


        //Login Page Button
        binding.loginButton.setOnClickListener {
            loginPage()
        }


        //Imperial and Metric Button
        binding.MetricButton.setOnClickListener {
            if(signUpFields.measurement == 0){
                binding.MeasurementImperialHiddenView.visibility = View.GONE
                binding.MeasurementMetricHiddenView.visibility = View.VISIBLE
                signUpFields.measurement = 1
                binding.HeightFeet.text.clear()
                binding.HeightInch.text.clear()
                binding.currentWeightlb.text.clear()
                binding.goalWeightlb.text.clear()
            }
        }

        binding.ImperialButton.setOnClickListener {
            if(signUpFields.measurement == 1){
                binding.MeasurementMetricHiddenView.visibility = View.GONE
                binding.MeasurementImperialHiddenView.visibility = View.VISIBLE
                signUpFields.measurement = 0
                binding.HeightCm.text.clear()
                binding.currentWeightKg.text.clear()
                binding.goalWeightkg.text.clear()
            }
        }

        //Initializing the page
        updateSection()

        //insertData()

    }

    private fun signUp() {
        TODO("Not yet implemented")
    }

    private fun insertData(){
        //Activity Level
        if(signUpFields.activityLevel == 0){
            binding.sedentary.isSelected = true
        } else if(signUpFields.activityLevel == 1){
            binding.lightlyActive.isSelected = true
        } else if(signUpFields.activityLevel == 2){
            binding.active.isSelected = true
        } else if(signUpFields.activityLevel == 3){
            binding.veryActive.isSelected = true
        }

        //Weight Goal
        if(signUpFields.weightGoal == 0){
            binding.loseWeight.isSelected = true
        } else if(signUpFields.weightGoal == 1){
            binding.maintainWeight.isSelected = true
        } else if(signUpFields.weightGoal == 2){
            binding.gainWeight.isSelected = true
        }

        //Measurement
        binding.HeightFeet.setText(signUpFields.heightFt.toString())
        binding.HeightInch.setText(signUpFields.heightIn.toString())
        binding.currentWeightlb.setText(signUpFields.weightlb.toString())
        binding.goalWeightlb.setText(signUpFields.goalWeightlb.toString())

        binding.HeightCm.setText(signUpFields.heightCm.toString())
        binding.currentWeightKg.setText(signUpFields.weightkg.toString())
        binding.goalWeightkg.setText(signUpFields.goalWeightkg.toString())

        //SexDOB
        if(signUpFields.sex == "male"){
            binding.Male.isSelected = true
        } else if(signUpFields.sex == "female"){
            binding.Female.isSelected = true
        }

        if(signUpFields.dob != null){
            var dateText  = signUpFields.dob!!.day.toString() + "/" + (signUpFields.dob!!.month + 1) + "/" + signUpFields.dob!!.year.toString()
            binding.date.text =dateText
        }
    }

    //On Click Functions

    fun onActiveLevelButtonClick(view: View) {
        Log.d(SIGNUP, view.id.toString())
        if(view.id == R.id.sedentary){
            signUpFields.activityLevel = 0
        } else if(view.id == R.id.lightlyActive){
            signUpFields.activityLevel = 1
        } else if(view.id == R.id.active){
            signUpFields.activityLevel = 2
        } else if(view.id == R.id.veryActive){
            signUpFields.activityLevel = 3
        } else{
            signUpFields.activityLevel = null
        }
    }

    fun weightGoalClick(view: View) {
        Log.d(SIGNUP, view.id.toString())
        if(view.id == R.id.loseWeight){
            signUpFields.weightGoal = 0
        } else if(view.id == R.id.maintainWeight){
            signUpFields.weightGoal = 1
        } else if(view.id == R.id.gainWeight){
            signUpFields.weightGoal = 2
        } else{
            signUpFields.weightGoal = null
        }
    }

    fun onSexClick(view: View) {
        Log.d(SIGNUP, view.id.toString())
        if(view.id == R.id.Male){
            signUpFields.sex = "male"
            binding.Male.setBackgroundColor(Color.parseColor("#ffffff"))
        } else if(view.id == R.id.Female){
            signUpFields.sex = "female"
            binding.Female.setBackgroundColor(Color.parseColor("#ffffff"))
        } else{
            signUpFields.sex = null
        }
    }

    //Switching Sections

    private fun activityLevelPage(){
        if(signUpFields.section != 0) {
            signUpFields.section = 0
            updateSection()
        }
    }

    private fun weightGoalPage(){
        if(signUpFields.section != 1) {
            signUpFields.section = 1
            updateSection()
        }
    }

    private fun measurementPage(){
        if(signUpFields.section != 2) {
            signUpFields.section = 2
            updateSection()
        }
    }

    private fun sexDOBPage(){
        if(signUpFields.section != 3) {
            signUpFields.section = 3
            updateSection()
        }
    }

    private fun signUpPage(){
        if(signUpFields.section != 4) {
            signUpFields.section = 4
            updateSection()
        }
    }

    private fun updateSection(){
        Log.d(SIGNUP, signUpFields.section.toString())
        if(signUpFields.section == 0){
            binding.ActivityLevelHiddenView.visibility = (View.VISIBLE)
            binding.WeightGoalHiddenView.visibility = (View.GONE)
            binding.SexDOBHiddenView.visibility = (View.GONE)
            binding.SignUpHiddenView.visibility = (View.GONE)
            binding.MeasurementMetricHiddenView.visibility = (View.GONE)
            binding.MeasurementImperialHiddenView.visibility = (View.GONE)
            binding.ImpMetricButtons.visibility = (View.GONE)
        } else if(signUpFields.section == 1){
            binding.ActivityLevelHiddenView.visibility = (View.GONE)
            binding.WeightGoalHiddenView.visibility = (View.VISIBLE)
            binding.SexDOBHiddenView.visibility = (View.GONE)
            binding.SignUpHiddenView.visibility = (View.GONE)
            binding.MeasurementMetricHiddenView.visibility = (View.GONE)
            binding.MeasurementImperialHiddenView.visibility = (View.GONE)
            binding.ImpMetricButtons.visibility = (View.GONE)
        } else if(signUpFields.section == 2){
            binding.ActivityLevelHiddenView.visibility = (View.GONE)
            binding.WeightGoalHiddenView.visibility = (View.GONE)
            binding.SexDOBHiddenView.visibility = (View.GONE)
            binding.SignUpHiddenView.visibility = (View.GONE)
            if(signUpFields.measurement == 0) {
                binding.MeasurementImperialHiddenView.visibility = (View.VISIBLE)
                binding.MeasurementMetricHiddenView.visibility = (View.GONE)
            } else if(signUpFields.measurement == 1) {
                binding.MeasurementImperialHiddenView.visibility = (View.VISIBLE)
                binding.MeasurementMetricHiddenView.visibility = (View.GONE)
            }
            binding.ImpMetricButtons.visibility = (View.VISIBLE)
        }else if(signUpFields.section == 3){
            binding.ActivityLevelHiddenView.visibility = (View.GONE)
            binding.WeightGoalHiddenView.visibility = (View.GONE)
            binding.SexDOBHiddenView.visibility = (View.VISIBLE)
            binding.SignUpHiddenView.visibility = (View.GONE)
            binding.MeasurementMetricHiddenView.visibility = (View.GONE)
            binding.MeasurementImperialHiddenView.visibility = (View.GONE)
            binding.ImpMetricButtons.visibility = (View.GONE)
        }else if(signUpFields.section == 4){
            binding.ActivityLevelHiddenView.visibility = (View.GONE)
            binding.WeightGoalHiddenView.visibility = (View.GONE)
            binding.SexDOBHiddenView.visibility = (View.GONE)
            binding.SignUpHiddenView.visibility = (View.VISIBLE)
            binding.MeasurementMetricHiddenView.visibility = (View.GONE)
            binding.MeasurementImperialHiddenView.visibility = (View.GONE)
            binding.ImpMetricButtons.visibility = (View.GONE)
        }
    }

    private fun loginPage(){
        finish()
    }

    //Checkers
    private fun activityLevelChecker(): Boolean {
        if(binding.sedentary.isSelected || binding.lightlyActive.isSelected ||binding.active.isSelected || binding.veryActive.isSelected){
            binding.ActivityLevelIcon.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_check_circle_24, null))
            return true
        } else
            return false
    }

    private fun weightGoalChecker(): Boolean {
        if(binding.loseWeight.isSelected || binding.maintainWeight.isSelected ||binding.gainWeight.isSelected){
            binding.WeightGoalIcon.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_check_circle_24, null))
            return true
        } else
            return false
    }

    private fun measurementChecker(): Boolean {
        if(signUpFields.measurement == 0){
            if(binding.HeightFeet.text.isNullOrEmpty() || binding.HeightInch.text.isNullOrEmpty() || binding.currentWeightlb.text.isNullOrEmpty() || binding.goalWeightlb.text.isNullOrEmpty()){
                binding.ActivityLevelIcon.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_check_circle_24, null))
                return true
            } else
                return false
        } else if(signUpFields.measurement == 1){
            if(binding.HeightCm.text.isNullOrEmpty() || binding.currentWeightKg.text.isNullOrEmpty() || binding.goalWeightkg.text.isNullOrEmpty()){
                binding.ActivityLevelIcon.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_check_circle_24, null))
                return true
            } else
                return false
        }
        else
            return false
    }

}