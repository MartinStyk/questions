package com.example.interview.task.vmcreate

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.interview.task.vmresources.FragmentWithResourcesViewModel

class FragmentWithResources : Fragment() {
    private val viewModel by viewModels<FragmentWithResourcesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.color.observe(viewLifecycleOwner) { Log.e("TEST", "color is $it") }
    }
}