package com.example.interview.repository

import com.example.interview.model.UserProfile
import javax.inject.Inject

class UserRepository @Inject constructor() {
    suspend fun getCurrentUser(): UserProfile {
        return UserProfile("Current", "User")
    }
}