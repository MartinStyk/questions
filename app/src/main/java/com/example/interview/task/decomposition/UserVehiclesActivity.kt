package com.example.interview.task.decomposition

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.interview.R
import com.example.interview.databinding.ActivityVehiclesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserVehiclesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVehiclesBinding
    private val viewModel by viewModels<UserVehiclesActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vehicles)

        viewModel.userName.observe(this) { name ->
            binding.name.text = name
        }

        viewModel.vehicles.observe(this) { vehicles ->
//            binding.vehicleList.adapter = Adapter(vehicles)
        }
    }

}