package com.example.smartgymkmp.viewmodels

import MemberRepository

// commonMain/kotlin/viewmodel/ViewModelFactory.kt
class ViewModelFactory(
    private val memberRepository: MemberRepository
) {
    fun createMemberViewModel(): MemberViewModel {
        return MemberViewModel(memberRepository)
    }
}