package com.example.smartgymkmp.model

// Move to `src/commonMain/kotlin/com/example/smartgymkmp/model/Enums.kt`
enum class MemberFilter(val title: String) {
    TODAY("Today"),
    THIS_WEEK("This Week"),
    ALL("All Members")
}

enum class MemberStatus {
    ACTIVE, PENDING, CONVERTED
}