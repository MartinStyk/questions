package com.sygic.tracker.manager.resources

import androidx.annotation.DimenRes

interface ResourcesManager {

    val currentLanguage: String

    fun getDimensionPixelSize(@DimenRes dimenRes: Int): Int

    fun readTextAssetsResource(fileName: String): String?
}