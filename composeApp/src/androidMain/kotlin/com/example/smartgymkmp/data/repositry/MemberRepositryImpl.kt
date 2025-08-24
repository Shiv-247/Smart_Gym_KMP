package com.example.smartgymkmp.data.repositry

import MemberRepository
import com.example.smartgymkmp.domain.model.Member
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseMemberRepository : MemberRepository {
    private val db = FirebaseFirestore.getInstance()

    override suspend fun getMembers(): List<Member> {
        val snapshot = db.collection("members").get().await()
        return snapshot.documents.mapNotNull { it.toObject(Member::class.java) }
    }

    override suspend fun addMember(member: Member) {
        db.collection("members").document(member.id).set(member).await()
    }

    override suspend fun calculateCalories(memberId: String): Int {
        val snapshot = db.collection("members").document(memberId).get().await()
        val weight = snapshot.getLong("weight")?.toInt() ?: 0 // kg
        val height = snapshot.getLong("height")?.toInt() ?: 0 // cm
        val age = snapshot.getLong("age")?.toInt() ?: 0
        val gender = snapshot.getString("gender") ?: "male"
        val activityLevel = snapshot.getString("activity") ?: "moderate"

        // Step 1: BMR
        val bmr = if (gender.lowercase() == "male") {
            (10 * weight) + (6.25 * height) - (5 * age) + 5
        } else {
            (10 * weight) + (6.25 * height) - (5 * age) - 161
        }

        // Step 2: Activity multiplier
        val multiplier = when (activityLevel.lowercase()) {
            "sedentary" -> 1.2
            "light" -> 1.375
            "moderate" -> 1.55
            "active" -> 1.725
            "very active" -> 1.9
            else -> 1.55
        }

        return (bmr * multiplier).toInt()
    }
}