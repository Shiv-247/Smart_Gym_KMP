package com.example.smartgymkmp.database

import com.example.smartgymkmp.data.local.MemberDao

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = SmartGymDatabase(databaseDriverFactory.createDriver())

    internal val memberDao = MemberDao(database)
//    internal val workoutDao = WorkoutDao(database)
//    internal val paymentDao = PaymentDao(database)
}