package com.example.interview.task.activitylifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.interview.R
import com.example.interview.api.UserApiRx
import com.example.interview.databinding.ActivityNetworkBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * What happens
 * - on rotation
 * - if network call is successful
 * - if network call fails
 * - if we close activity before network call is done
 */
@AndroidEntryPoint
class NetworkActivityRx : AppCompatActivity() {

    @Inject
    lateinit var userApi: UserApiRx

    private lateinit var binding: ActivityNetworkBinding

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_network)

        disposables.add(
            userApi.getUserProfile()
                .subscribeOn(Schedulers.io())
                .subscribe { profile ->
                    binding.name.text = "${profile.name} ${profile.surname}"

                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.container, ProfileFragment())
                        .commit()
                })
    }

}