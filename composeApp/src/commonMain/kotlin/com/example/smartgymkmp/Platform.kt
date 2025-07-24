package com.example.smartgymkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform