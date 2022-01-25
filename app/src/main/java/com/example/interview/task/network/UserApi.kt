package com.example.interview.task.network

import com.example.interview.model.UserProfile
import retrofit2.http.*


/**
 * We need to send token header in every HTTP request.
 * Can we achieve it more efficiently?
 */
interface UserTestApi {

    @Headers("Accept: application/json", "Content-Type: application/json-patch+json")
    @GET("user/get-user-profile")
    fun getUserProfile(@Header("Authorization") bearerToken: String): UserProfile

    @Headers("Accept: application/json", "Content-Type: application/json-patch+json")
    @POST("user/create-user-profile")
    fun createUserProfile(@Header("Authorization") bearerToken: String, userProfile: UserProfile)

    @Headers("Accept: application/json", "Content-Type: application/json-patch+json")
    @PUT("user/update-user-profile")
    fun updateUserProfile(@Header("Authorization") bearerToken: String, userProfile: UserProfile)

}