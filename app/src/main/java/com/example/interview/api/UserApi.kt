package com.example.interview.api

import com.example.interview.model.UserProfile
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserApi {

    @Headers("Accept: application/json", "Content-Type: application/json-patch+json")
    @GET("user/get-user-profile")
    fun getUserProfile(): UserProfile

}