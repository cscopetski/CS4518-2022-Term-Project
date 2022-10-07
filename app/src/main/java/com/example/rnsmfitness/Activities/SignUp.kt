package com.example.rnsmfitness.Activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.rnsmfitness.ModelViews.SignUpModelView
import com.example.rnsmfitness.R
import com.example.rnsmfitness.databinding.ActivitySignUpBinding
import java.util.*


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
        binding.HeightInch.doOnTextChanged { text, start, before, count ->
            signUpFields.heightIn = text.toString().toDouble()
        }

        binding.HeightFeet.doOnTextChanged { text, start, before, count ->
            signUpFields.heightFt = text.toString().toDouble()
        }

        binding.HeightCm.doOnTextChanged { text, start, before, count ->
            signUpFields.heightCm = text.toString().toDouble()
        }

        binding.currentWeightlb.doOnTextChanged { text, start, before, count ->
            signUpFields.weightlb = text.toString().toDouble()
        }

        binding.currentWeightKg.doOnTextChanged { text, start, before, count ->
            signUpFields.weightkg = text.toString().toDouble()
        }

        binding.goalWeightlb.doOnTextChanged { text, start, before, count ->
            signUpFields.goalWeightlb = text.toString().toDouble()
        }

        binding.goalWeightkg.doOnTextChanged { text, start, before, count ->
            signUpFields.goalWeightkg = text.toString().toDouble()
        }

        binding.FirstName.doOnTextChanged{text, start, before, count ->
            signUpFields.firstName = text.toString()
        }

        binding.LastName.doOnTextChanged{text, start, before, count ->
            signUpFields.lastName = text.toString()
        }

        binding.email.doOnTextChanged{text, start, before, count ->
            signUpFields.email = text.toString()
        }

        binding.password.doOnTextChanged{text, start, before, count ->
            signUpFields.password = text.toString()
            if(signUpFields.password != null || signUpFields.password != ""){
                binding.password.hint = ""
            } else{
                binding.password.hint = "Password..."
            }
        }

        binding.confirmPassword.doOnTextChanged{text, start, before, count ->
            if(signUpFields.confirmPassword != text) {
                signUpFields.confirmPassword = text.toString()
                if (signUpFields.confirmPassword != null || signUpFields.confirmPassword != "") {
                    binding.confirmPassword.hint = ""
                } else {
                    binding.confirmPassword.hint = "Confirm Password..."
                }
            }
        }


        //Set on clicker
        binding.date.setOnClickListener(View.OnClickListener { // calender class's instance and get current date , month and year from calender
            val c = Calendar.getInstance()
            val mYear = c[Calendar.YEAR] // current year
            val mMonth = c[Calendar.MONTH] // current month
            val mDay = c[Calendar.DAY_OF_MONTH] // current day
            // date picker dialog
            datePickerDialog = DatePickerDialog(this@SignUp,
                { view, year, monthOfYear, dayOfMonth -> // set day of month , month and year value in the edit text
                    binding.date.setText(
                        dayOfMonth.toString() + "/"
                                + (monthOfYear + 1) + "/" + year
                    )
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
            }
        }

        binding.ImperialButton.setOnClickListener {
            if(signUpFields.measurement == 1){
                binding.MeasurementMetricHiddenView.visibility = View.GONE
                binding.MeasurementImperialHiddenView.visibility = View.VISIBLE
                signUpFields.measurement = 0
            }
        }

        //Initializing the page
        updateSection()

    }

    private fun signUp() {
        TODO("Not yet implemented")
    }

    //On Click Functions

    fun onActiveLevelButtonClick(view: View) {
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

    fun WeightGoalClick(view: View) {
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
        if(view.id == R.id.Male){
            signUpFields.sex = "Male"
            binding.Male.setBackgroundColor(Color.parseColor("#ffffff"))
        } else if(view.id == R.id.Female){
            signUpFields.sex = "Female"
            binding.Female.setBackgroundColor(Color.parseColor("#ffffff"))
        } else{
            signUpFields.sex = null
        }
    }

    //Switching Sections

    fun activityLevelPage(){
        if(signUpFields.section != 0) {
            signUpFields.section = 0
            updateSection()
        }
    }

    fun weightGoalPage(){
        if(signUpFields.section != 1) {
            signUpFields.section = 1
            updateSection()
        }
    }

    fun measurementPage(){
        if(signUpFields.section != 2) {
            signUpFields.section = 2
            updateSection()
        }
    }

    fun sexDOBPage(){
        if(signUpFields.section != 3) {
            signUpFields.section = 3
            updateSection()
        }
    }

    fun signUpPage(){
        if(signUpFields.section != 4) {
            signUpFields.section = 4
            updateSection()
        }
    }

    fun updateSection(){
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

    fun loginPage(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}
