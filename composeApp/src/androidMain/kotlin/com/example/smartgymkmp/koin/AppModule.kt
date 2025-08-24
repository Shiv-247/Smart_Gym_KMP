package com.example.smartgymkmp.koin

import MemberRepository
import com.example.smartgymkmp.data.repositry.FirebaseMemberRepository
import org.koin.dsl.module

val androidModule = module {
    // Member Repository implementation
    single<MemberRepository> { FirebaseMemberRepository() }
}
