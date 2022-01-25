package com.example.interview.dependencyinjection

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.example.interview.api.UserApi
import com.example.interview.api.UserApiRx
import com.example.interview.model.UserProfile
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    @ForApplication
    fun providesResources(application: Application): Resources = application.resources

    @Provides
    fun provideUserApi(): UserApi {
        return Retrofit.Builder()
            .baseUrl("https://userapi.mycompany.com")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(UserApi::class.java)
    }

    @Provides
    fun provideUserApiRx(): UserApiRx {
        return object : UserApiRx {
            override fun getUserProfile(): Single<UserProfile> {
                return Single.just(UserProfile("Name", "Love")).delay(5, TimeUnit.SECONDS)
            }
        }
//            .baseUrl("https://userapi.mycompany.com")
//            .client(OkHttpClient.Builder().build())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(Gson()))
//            .build()
//            .create(UserApiRx::class.java)
    }

}