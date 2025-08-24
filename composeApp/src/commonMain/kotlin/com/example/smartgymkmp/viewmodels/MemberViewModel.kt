package com.example.smartgymkmp.viewmodels

import MemberRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.smartgymkmp.database.Members
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

    private val _calories = MutableStateFlow(0)
    val calories: StateFlow<Int> = _calories

    fun loadMembers() {
        viewModelScope.launch {
            _members.value = repository.getMembers()
        }
    }

    fun calculateCalories(memberId: String) {
        viewModelScope.launch {
            _calories.value = repository.calculateCalories(memberId)
        }
    }
}