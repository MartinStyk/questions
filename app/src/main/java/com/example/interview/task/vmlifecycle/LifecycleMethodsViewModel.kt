package com.example.interview.task.vmlifecycle

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

/**
 * What is the difference between these lifecycle methods? especially onCleared/onDestroy
 * What is the behavior on rotation?
 * What about androidx.lifecycle.viewModelScope
 */
class LifecycleMethodsViewModel: ViewModel(), DefaultLifecycleObserver {

    lateinit var createdScope : CoroutineScope
    private val initToClearScope = CoroutineScope(Dispatchers.Default)

    init {
        initToClearScope.writeLogs("InitToClearScope")
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        createdScope = CoroutineScope(Dispatchers.Default)
        createdScope.writeLogs("createdScope")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.writeLogs("ViewModelScope")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        createdScope.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        initToClearScope.cancel()
    }

    private fun CoroutineScope.writeLogs(log: String) = launch {
        while (isActive) {
            Log.e("TEST", log)
            delay(3000)
        }
    }

}