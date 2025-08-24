package com.example.smartgymkmp.data.repositry

import com.example.smartgymkmp.domain.model.AuthResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.text.get

actual suspend fun platformLogin(email: String, password: String): AuthResult {
    val db = Firebase.firestore
    val querySnapshot = db.collection("gymOwners")
        .whereEqualTo("email", email)
        .whereEqualTo("password", password)
        .get()
        .await()

    return if (!querySnapshot.isEmpty) AuthResult.Success
    else AuthResult.Error("Invalid credentials")
}
