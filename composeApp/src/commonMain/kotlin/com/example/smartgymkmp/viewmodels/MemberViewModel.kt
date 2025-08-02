package com.example.smartgymkmp.viewmodels

import MemberRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.smartgymkmp.database.Members
import com.example.smartgymkmp.model.MemberEntity
import com.example.smartgymkmp.network.fetchMembersFromFirestore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class MemberViewModel(
    private val repository: MemberRepository
) : ViewModel() {

    var memberList by mutableStateOf<List<Members>>(emptyList())

    val allMembers: StateFlow<List<MemberEntity>> = repository.getAllMembers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addMember(member: MemberEntity) {
        viewModelScope.launch {
            repository.insert(member)
        }
    }

    fun deleteMember(member: MemberEntity) = viewModelScope.launch {
        repository.delete(member = member)
    }

    fun getMemberById(id: String): Flow<Members?> {
        return repository.getMemberById(id).map { entity ->
            entity?.toMember()
        }
    }

    fun updateMember(member: MemberEntity) {
        viewModelScope.launch {
            repository.update(member)
        }
    }

    fun loadMembers(gymOwnerId: String) {
        viewModelScope.launch {
            val remoteMembers = fetchMembersFromFirestore(gymOwnerId)
            val localEntities = remoteMembers.map { it.toEntity() }
            repository.insertAll(localEntities)
        }
    }

    fun Members.toEntity(): MemberEntity {
        return MemberEntity(
            id = this.id,
            name = this.name,
            phoneNumber = this.phoneNumber,
            fitnessGoal = this.fitnessGoal,
            startDate = this.startDate,
            avatarColor = this.avatarColor,
            notes = this.notes,
            status = this.status,
            age = this.age,
            activePlan = this.activePlan
        )
    }

    fun MemberEntity.toMember(): Members {
        return Members(
            id = id,
            name = name,
            phoneNumber = phoneNumber,
            fitnessGoal = fitnessGoal,
            startDate = startDate,
            avatarColor = avatarColor,
            notes = notes,
            status = status,
            age = age,
            activePlan = activePlan
        )
    }
}
