package com.example.smartgymkmp.data.repositry

import MemberRepository
import android.util.Log
import com.example.smartgymkmp.domain.model.ActivityLevel
import com.example.smartgymkmp.domain.model.CalorieResult
import com.example.smartgymkmp.domain.model.FitnessGoal
import com.example.smartgymkmp.domain.model.Member
import com.example.smartgymkmp.domain.model.usecase.CalorieCalculator
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseMemberRepository(
    private val calorieCalculator: CalorieCalculator
) : MemberRepository {
    private val db = FirebaseFirestore.getInstance()

    override suspend fun getMembers(): List<Member> {
        val snapshot = db.collection("members").get().await()
        return snapshot.documents.mapNotNull { it.toObject(Member::class.java) }
    }

    override suspend fun addMember(member: Member) {
        db.collection("members").document(member.id).set(member).await()
    }

    override suspend fun calculateCalories(
        memberId: String,
        activityLevel: ActivityLevel
    ): CalorieResult {
        return try {
            val snapshot = db.collection("members").document(memberId).get().await()
            Log.d("CalorieCalculation", "Snapshot loaded: exists=${snapshot.exists()}")

            if (!snapshot.exists()) {
                Log.e("CalorieCalculation", "Member document not found")
                return CalorieResult(0.0, 0.0, 0.0)
            }

            // Manual data extraction instead of using toObject
            val data = snapshot.data
            if (data == null) {
                Log.e("CalorieCalculation", "Member document has no data")
                return CalorieResult(0.0, 0.0, 0.0)
            }

            // Log the data to see what's coming from Firestore
            Log.d("CalorieCalculation", "Member data: $data")

            // Convert activityLevel enum to string
            val activityLevelStr = when (activityLevel) {
                ActivityLevel.SEDENTARY -> "sedentary"
                ActivityLevel.LIGHT -> "light"
                ActivityLevel.MODERATE -> "moderate"
                ActivityLevel.ACTIVE -> "active"
                ActivityLevel.VERY_ACTIVE -> "very_active"
            }

            // Manually create Member object with safe extraction and passed activityLevel
            val member = Member(
                id = memberId,
                name = data["name"] as? String ?: "Unknown",
                age = (data["age"] as? Number)?.toInt() ?: 30,
                gender = data["gender"] as? String ?: "male",
                weight = (data["weight"] as? Number)?.toDouble() ?: 70.0,
                height = (data["height"] as? Number)?.toDouble() ?: 170.0,
                activityLevel = activityLevelStr  // Use the passed activityLevel
            )

            Log.d("CalorieCalculation", "Created member: $member")

            // Calculate calories with the member data
            val result = calorieCalculator.calculateCalories(member, FitnessGoal.MAINTENANCE)
            Log.d(
                "CalorieCalculation",
                "Calculated result: BMR=${result.bmr}, TDEE=${result.tdee}, Recommended=${result.recommendedCalories}"
            )

            result
        } catch (e: Exception) {
            Log.e("CalorieCalculation", "Error calculating calories", e)
            CalorieResult(0.0, 0.0, 0.0)
        }
    }

    // Add this function to FirebaseMemberRepository
   override suspend fun ensureMemberExists(memberId: String) {
        val snapshot = db.collection("members").document(memberId).get().await()
        if (!snapshot.exists()) {
            Log.d("CalorieCalculation", "Creating default member with ID: $memberId")

            // Create a default member
            val defaultMember = Member(
                id = memberId,
                name = "Default User",
                age = 30,
                gender = "male",
                weight = 70.0,
                height = 170.0,
                activityLevel = "moderate"
            )

            // Add to Firestore
            addMember(defaultMember)
        }
    }
}

