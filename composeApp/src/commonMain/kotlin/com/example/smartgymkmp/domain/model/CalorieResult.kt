package com.example.smartgymkmp.domain.model

data class CalorieResult(
    val bmr: Double,
    val tdee: Double,
    val recommendedCalories: Double
) {
    companion object {
        fun safe(bmr: Double, tdee: Double, recommended: Double): CalorieResult {
            return CalorieResult(
                bmr = bmr.takeIf { it.isFinite() } ?: 0.0,
                tdee = tdee.takeIf { it.isFinite() } ?: 0.0,
                recommendedCalories = recommended.takeIf { it.isFinite() } ?: 0.0
            )
        }
    }
}
