package com.example.rnsmfitness.Entities

import java.sql.Date


//Weight kg and Height cm should be in metric, so change it to metric if in imperial when inputted by user
//units = "imperial" || "metric"
//activitylevel = 0,1,2,3
//weightgoal = 0,1,2
//gender = "male" || "female"
data class SignUpCredentials(val activitylevel : Int, val weightgoal : Int, val height : Double, val weight : Double, val goalweight : Double, val firstname : String, val lastname : String, val email : String, val password : String, val gender : String, val dob : Date, val units: String = "metric")
