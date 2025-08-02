import org.gradle.kotlin.dsl.implementation
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.sqldelight)
}

// SQLDelight configuration for version 1.5.x
sqldelight {
    database("SmartGymDatabase") {
        packageName = "com.example.smartgymkmp.database"
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(compose.material)
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("androidx.compose.ui:ui:1.6.0")
            implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
            implementation("androidx.compose.material:material:1.6.0")
            implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
            // SQLDelight Android driver for version 1.5.x
            implementation("com.squareup.sqldelight:android-driver:1.5.5")

            //firebase
            implementation("com.google.firebase:firebase-firestore-ktx:24.10.3")

            implementation("io.insert-koin:koin-android:3.5.0")
        }

        iosMain.dependencies {
            // SQLDelight iOS driver for version 1.5.x
            implementation("com.squareup.sqldelight:native-driver:1.5.5")
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.0-beta03")
            implementation(compose.materialIconsExtended)
            // SQLDelight runtime for version 1.5.x
            implementation("com.squareup.sqldelight:runtime:1.5.5")
            implementation("com.squareup.sqldelight:coroutines-extensions:1.5.5")


            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
            implementation("com.benasher44:uuid:0.8.1")

            implementation("io.insert-koin:koin-core:3.5.0")
            implementation("io.insert-koin:koin-android:3.5.0")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.smartgymkmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.smartgymkmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}