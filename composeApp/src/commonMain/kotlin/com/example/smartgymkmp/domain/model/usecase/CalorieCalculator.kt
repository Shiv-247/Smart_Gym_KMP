package com.example.smartgymkmp.domain.model.usecase

import com.example.smartgymkmp.domain.model.ActivityLevel
import com.example.smartgymkmp.domain.model.CalorieResult
import com.example.smartgymkmp.domain.model.FitnessGoal
import com.example.smartgymkmp.domain.model.Member

class CalorieCalculator {

    fun calculateBMR(member: Member): Double {
        val gender = member.gender?.lowercase() ?: "male"
        return if (gender == "male") {
            10 * member.weight + 6.25 * member.height - 5 * member.age + 5
        } else {
            10 * member.weight + 6.25 * member.height - 5 * member.age - 161
        }
    }

    fun calculateTDEE(bmr: Double, activityLevel: String?): Double {
        val level = activityLevel?.lowercase() ?: "moderate"
        val multiplier = when (level) {
            "sedentary" -> ActivityLevel.SEDENTARY.multiplier
            "light" -> ActivityLevel.LIGHT.multiplier
            "moderate" -> ActivityLevel.MODERATE.multiplier
            "active" -> ActivityLevel.ACTIVE.multiplier
            "very_active" -> ActivityLevel.VERY_ACTIVE.multiplier
            else -> ActivityLevel.MODERATE.multiplier
        }
        return bmr * multiplier
    }


    fun recommendCalories(tdee: Double, goal: FitnessGoal): Double {
        return when (goal) {
            FitnessGoal.WEIGHT_LOSS -> tdee - 500
            FitnessGoal.WEIGHT_GAIN -> tdee + 500
            FitnessGoal.MUSCLE_GAIN -> tdee + 300
            FitnessGoal.MAINTENANCE -> tdee
        }
    }

    fun calculateCalories(member: Member, goal: FitnessGoal = FitnessGoal.MAINTENANCE): CalorieResult {
        val bmr = calculateBMR(member)
        val tdee = calculateTDEE(bmr, member.activityLevel)
        val recommended = recommendCalories(tdee, goal)

        return CalorieResult(
            bmr = bmr,
            tdee = tdee,
            recommendedCalories = recommended
        )
    }
}