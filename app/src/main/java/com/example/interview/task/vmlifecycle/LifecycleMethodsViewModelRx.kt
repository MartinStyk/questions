package com.example.interview.task.vmlifecycle

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * What is the difference between these lifecycle methods? especially onCleared/onDestroy
 * What is the behavior on rotation?
 */
class LifecycleMethodsViewModelRx : ViewModel(), DefaultLifecycleObserver {

    private var createdScopeDisposable: Disposable? = null
    private var initToClearScopeDisposable: Disposable? = null

    init {
        initToClearScopeDisposable = startLogs("initToClearScope")
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        createdScopeDisposable = startLogs("createdScope")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        createdScopeDisposable?.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        initToClearScopeDisposable?.dispose()
    }

    private fun startLogs(log: String) =
        Observable.interval(3, TimeUnit.SECONDS)
            .subscribe {
                Log.e("TEST", log)
            }

}