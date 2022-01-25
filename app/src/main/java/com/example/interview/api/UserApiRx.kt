package com.example.interview.api

import com.example.interview.model.UserProfile
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserApiRx {

    @Headers("Accept: application/json", "Content-Type: application/json-patch+json")
    @GET("user/get-user-profile")
    fun getUserProfile(): Single<UserProfile>

}