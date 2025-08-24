package com.example.smartgymkmp.viewmodels

import MemberRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.smartgymkmp.database.Members
import com.example.smartgymkmp.domain.model.ActivityLevel
import com.example.smartgymkmp.domain.model.CalorieResult
import com.example.smartgymkmp.domain.model.Member
import com.example.smartgymkmp.domain.model.usecase.CalculateCaloriesUseCase
import com.example.smartgymkmp.model.MemberEntity
import com.example.smartgymkmp.network.fetchMembersFromFirestore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class MemberViewModel(
    private val repository: MemberRepository
) : ViewModel() {

    private val _members = MutableStateFlow<List<Member>>(emptyList())
    val members: StateFlow<List<Member>> = _members

    private val _calories = MutableStateFlow(CalorieResult(0.0, 0.0, 0.0)) // 👈 updated
    val calories: StateFlow<CalorieResult> = _calories

    fun loadMembers() {
        viewModelScope.launch {
            _members.value = repository.getMembers()
        }
    }

    fun calculateCalories(memberId: String, activityLevel: ActivityLevel) {
        viewModelScope.launch {
            val result = repository.calculateCalories(memberId, activityLevel)
            _calories.value = result
        }
    }

    // Add this to MemberViewModel
    fun ensureMemberExists(memberId: String) {
        viewModelScope.launch {
            repository.ensureMemberExists(memberId)
        }
    }
}
