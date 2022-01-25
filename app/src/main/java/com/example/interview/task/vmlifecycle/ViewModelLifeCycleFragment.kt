package com.example.interview.task.vmlifecycle

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class ViewModelLifeCycleFragment : Fragment() {

    private val viewModel: LifecycleMethodsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }

}