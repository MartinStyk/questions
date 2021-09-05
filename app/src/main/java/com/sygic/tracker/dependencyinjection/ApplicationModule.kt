package com.sygic.tracker.dependencyinjection

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sygic.tracker.dependencyinjection.util.ForApplication
import com.sygic.tracker.manager.location.LocationManager
import com.sygic.tracker.manager.location.LocationManagerImpl
import com.sygic.tracker.manager.resources.ResourcesManager
import com.sygic.tracker.manager.resources.ResourcesManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.location.LocationManager as AndroidLocationManager

@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {

    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(application: Application): Context =
        application.applicationContext

    @Provides
    @Singleton
    @ForApplication
    fun providesResources(application: Application): Resources = application.resources

    @Provides
    @Singleton
    fun providePackageManager(application: Application): PackageManager = application.packageManager

    @Provides
    @Singleton
    fun provideContentResolver(application: Application): ContentResolver =
        application.contentResolver

    @Provides
    @Singleton
    fun provideAndroidLocationManager(application: Application): AndroidLocationManager =
        application.getSystemService(Context.LOCATION_SERVICE) as AndroidLocationManager

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun provideLocationManager(locationManagerImpl: LocationManagerImpl): LocationManager =
        locationManagerImpl

    @Provides
    @Singleton
    fun provideResourcesManager(resourcesManagerImpl: ResourcesManagerImpl): ResourcesManager =
        resourcesManagerImpl

}
