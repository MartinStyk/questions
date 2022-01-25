package com.example.interview.task.activitylifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.interview.R
import com.example.interview.api.UserApi
import com.example.interview.databinding.ActivityNetworkBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * What happens
 * - on rotation
 * - if network call is successful
 * - if network call fails
 * - if we close activity before network call is done
 */
@AndroidEntryPoint
class NetworkActivity : AppCompatActivity() {

    @Inject
    lateinit var userApi: UserApi

    private lateinit var binding: ActivityNetworkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_network)

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val profile = userApi.getUserProfile()
                binding.name.text = "${profile.name} ${profile.surname}"

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, ProfileFragment())
                    .commit()
            }
        }
    }

}