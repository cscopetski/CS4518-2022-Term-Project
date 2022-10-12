package com.example.rnsmfitness.Entities

import java.sql.Date

data class SignUpCredentials(val activitylevel : Int, val weightgoal : Int, val height : Double, val weight : Double, val goalweight : Double, val firstname : String, val lastname : String, val email : String, val password : String, val gender : String, val dob : Date, val units: String = "metric")
