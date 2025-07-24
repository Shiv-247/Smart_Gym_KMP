package com.example.smartgymkmp.data.local

import com.example.smartgymkmp.database.Members
import com.example.smartgymkmp.database.SmartGymDatabase
import kotlinx.coroutines.flow.Flow
import com.example.smartgymkmp.model.MemberEntity
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map


class MemberDao(private val db: SmartGymDatabase) {

    private val queries get() = db.memberQueries

    fun getAllMembers(): Flow<List<MemberEntity>> =
        queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toEntity() } }

    fun getMemberById(id: String): Flow<MemberEntity?> =
        queries.selectById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toEntity() }


    fun insertMember(member: MemberEntity) {
        queries.insertMember(
            id = member.id,
            name = member.name,
            phoneNumber = member.phoneNumber,
            fitnessGoal = member.fitnessGoal,
            startDate = member.startDate,
            avatarColor = member.avatarColor,
            notes = member.notes,
            status = member.status,
            age = member.age,
            activePlan = member.activePlan
        )
    }

    fun deleteMember(member: MemberEntity) {
        queries.deleteById(member.id)
    }

    fun insertAll(membersList: List<MemberEntity>) {
        queries.transaction {
            membersList.forEach { insertMember(it) }
        }
    }

    fun updateMember(member: MemberEntity) {
        queries.updateMember(
            name = member.name,
            phoneNumber = member.phoneNumber,
            fitnessGoal = member.fitnessGoal,
            startDate = member.startDate,
            avatarColor = member.avatarColor,
            notes = member.notes,
            status = member.status,
            age = member.age,
            activePlan = member.activePlan,
            id = member.id // must be last as per SQL
        )
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

