package com.example.smartgymkmp.network

import com.example.smartgymkmp.database.Members

expect suspend fun fetchMembersFromFirestore(gymOwnerId: String): List<Members>