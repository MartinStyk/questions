package com.example.interview.repository

import android.content.res.Resources
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import com.example.interview.dependencyinjection.ForApplication
import javax.inject.Singleton

@Singleton
class ResourcesRepository(@ForApplication private val resources: Resources) {
    fun getColor(@ColorRes colorRes: Int) = ResourcesCompat.getColor(resources, colorRes, null)
}