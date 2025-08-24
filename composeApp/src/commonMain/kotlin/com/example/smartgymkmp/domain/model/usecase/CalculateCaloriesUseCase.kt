package com.example.smartgymkmp.domain.model.usecase

import com.example.smartgymkmp.domain.model.Member
import kotlin.math.roundToInt

class CalculateCaloriesUseCase {

    fun execute(member: Member): Double {
        val bmr = if (member.gender.lowercase() == "male") {
            88.362 + (13.397 * member.weight) + (4.799 * member.height) - (5.677 * member.age)
        } else {
            447.593 + (9.247 * member.weight) + (3.098 * member.height) - (4.330 * member.age)
        }

        val multiplier = when (member.activityLevel.lowercase()) {
            "sedentary" -> 1.2
            "light" -> 1.375
            "moderate" -> 1.55
            "active" -> 1.725
            "very_active" -> 1.9
            else -> 1.2
        }

        return (bmr * multiplier).roundToInt().toDouble()
    }
}