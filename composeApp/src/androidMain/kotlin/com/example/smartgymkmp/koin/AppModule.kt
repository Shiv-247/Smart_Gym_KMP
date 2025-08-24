package com.example.smartgymkmp.koin

import MemberRepository
import com.example.smartgymkmp.data.repositry.FirebaseMemberRepository
import com.example.smartgymkmp.domain.model.usecase.CalorieCalculator
import org.koin.dsl.module

val androidModule = module {
    // provide CalorieCalculator
    single { CalorieCalculator() }

    // provide MemberRepository with calorieCalculator
    single<MemberRepository> { FirebaseMemberRepository(get()) }
}

