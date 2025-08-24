package com.example.smartgymkmp.domain.model

data class Member(
    val id: String = "",
    val name: String = "",
    val gender: String = "male",
    val weight: Double = 0.0,
    val height: Double = 0.0,
    val age: Int = 0,
    val activityLevel: String = "moderate",
    val calories: Double = 0.0 // calculated calories
)


enum class ActivityLevel(val multiplier: Double) {
    SEDENTARY(1.2),
    LIGHT(1.375),
    MODERATE(1.55),
    ACTIVE(1.725),
    VERY_ACTIVE(1.9)
}

enum class FitnessGoal { WEIGHT_LOSS, WEIGHT_GAIN, MAINTENANCE, MUSCLE_GAIN }