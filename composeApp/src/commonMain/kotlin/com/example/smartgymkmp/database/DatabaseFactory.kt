package com.example.smartgymkmp.database

import com.squareup.sqldelight.db.SqlDriver

// commonMain/kotlin/database/DatabaseFactory.kt
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}