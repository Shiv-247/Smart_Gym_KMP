package com.example.smartgymkmp.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.smartgymkmp.database.Members
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
actual suspend fun fetchMembersFromFirestore(gymOwnerId: String): List<Members> {
    return try {
        val snapshot = FirebaseFirestore.getInstance()
            .collection("gym_owners")
            .document(gymOwnerId)
            .collection("members")
            .get()
            .await()

        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

        snapshot.documents.mapNotNull { doc ->
            try {
                Members(
                    id = doc.id,
                    name = doc.getString("name") ?: "",
                    age = doc.getLong("age")?.toInt() ?: 0,
                    phoneNumber = doc.getString("contact") ?: "",
                    activePlan = doc.getString("active_plan") ?: "",
                    startDate = try {
                        val dateStr = doc.getString("startDate") ?: ""
                        LocalDate.parse(dateStr, formatter).toString()
                    } catch (e: Exception) {
                        LocalDate.now().toString()
                    },
                    fitnessGoal = doc.getString("fitnessGoal") ?: "",
                    avatarColor = doc.getLong("avatarColor") ?: 0L,
                    notes = doc.getString("notes") ?: "",
                    status = doc.getString("status") ?: ""
                )

            } catch (e: Exception) {
                null
            }
        }

    } catch (e: Exception) {
        emptyList()
    }
}
