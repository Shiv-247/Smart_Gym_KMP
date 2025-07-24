package com.example.smartgymkmp.model

data class MemberEntity(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val fitnessGoal: String,
    val startDate: String,          // LocalDate => stored as String (ISO format)
    val avatarColor: Long,
    val notes: String,
    val status: String,
    val age: Int = 0,
    val activePlan: String = ""
)
