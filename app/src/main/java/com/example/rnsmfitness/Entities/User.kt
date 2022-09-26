package com.example.rnsmfitness.Entities

data class User(
    var id: Int = 0,

    val firstname: String,

    val lastname: String,

    val email: String,

    val password: String,

    val units: String,

    val dob: String,

    val age: Int,

    val gender: String?
)