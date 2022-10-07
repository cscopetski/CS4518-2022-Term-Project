package com.example.rnsmfitness.ModelViews

import android.app.DatePickerDialog
import android.widget.Button
import androidx.lifecycle.ViewModel
import java.util.*

class SignUpModelView : ViewModel() {

    var section : Int = 0

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

    //Sign Up

    var firstName : String? = null

    var lastName : String? = null

    var email : String? = null

    var password : String? = null

    var confirmPassword : String? = null

}