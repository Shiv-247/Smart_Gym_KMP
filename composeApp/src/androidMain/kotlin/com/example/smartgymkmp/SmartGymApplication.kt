package com.example.smartgymkmp

import android.app.Application
import android.util.Log
import com.example.smartgymkmp.di.appModule
import com.example.smartgymkmp.koin.androidModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SmartGymApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidLogger()
            androidContext(this@SmartGymApplication)
            modules(appModule, androidModule)
        }
    }
}