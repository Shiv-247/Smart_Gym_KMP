package com.example.smartgymkmp.domain.model

data class Member(
    val id: String = "",
    val name: String = "",
    val age: Int = 0,
    val gender: String = "", // "male" or "female"
    val height: Double = 0.0, // cm
    val weight: Double = 0.0, // kg
    val activityLevel: String = "", // sedentary, light, moderate, active, very_active
    val calories: Double = 0.0 // calculated calories
)