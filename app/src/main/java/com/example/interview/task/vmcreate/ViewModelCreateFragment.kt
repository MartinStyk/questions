package com.example.interview.task.vmcreate

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * What is the difference between VM1 and VM2 initialization?
 */
class TestViewModel: ViewModel(){
    private val _userName = MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    init {
        viewModelScope.launch {
            delay(5000)
            _userName.value = "This is me"
        }
    }
}

class ViewModelCreateFragment: Fragment() {
    private val viewModel = TestViewModel()
    private val viewModel2 by viewModels<TestViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userName.observe(viewLifecycleOwner) { Log.e("TEST", "$it viewModel")}
        viewModel2.userName.observe(viewLifecycleOwner) { Log.e("TEST", "$it viewModel2")}
    }
}